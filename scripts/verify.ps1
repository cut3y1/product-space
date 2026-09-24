#requires -Version 7.0
param([string]$BaseUrl = 'http://localhost:8080')
$ErrorActionPreference = 'Stop'
$id = $null
function Assert-True($condition, $message) { if (-not $condition) { throw $message } }
try {
    $name = '验收商品-' + [guid]::NewGuid().ToString('N')
    $body = @{ name=$name; price=59.90; stock=100; description='自动验收' } | ConvertTo-Json
    $created = Invoke-WebRequest "$BaseUrl/api/products" -Method Post -ContentType 'application/json; charset=utf-8' -Body $body
    Assert-True ($created.StatusCode -eq 201) 'Create must return 201'
    $product = $created.Content | ConvertFrom-Json
    $id = $product.id
    $detail = Invoke-RestMethod "$BaseUrl/api/products/$id"
    Assert-True ($detail.name -eq $name -and $detail.price -eq 59.90) 'Detail differs from saved data'
    $list = Invoke-RestMethod "$BaseUrl/api/products?keyword=$([uri]::EscapeDataString($name))&page=0&size=1"
    Assert-True ($list.totalElements -eq 1 -and $list.content[0].id -eq $id) 'Search/pagination failed'
    $body = @{ name=$name; price=99.50; stock=12; description='已修改' } | ConvertTo-Json
    $null = Invoke-RestMethod "$BaseUrl/api/products/$id" -Method Put -ContentType 'application/json; charset=utf-8' -Body $body
    $updated = Invoke-RestMethod "$BaseUrl/api/products/$id"
    Assert-True ($updated.stock -eq 12 -and $updated.price -eq 99.50) 'Update did not persist'
    $invalid = Invoke-WebRequest "$BaseUrl/api/products" -Method Post -ContentType 'application/json' -Body '{"name":"","price":-1,"stock":-1}' -SkipHttpErrorCheck
    Assert-True ($invalid.StatusCode -eq 400) 'Invalid input must return 400'
    $deleted = Invoke-WebRequest "$BaseUrl/api/products/$id" -Method Delete
    Assert-True ($deleted.StatusCode -eq 204) 'Delete must return 204'
    $missing = Invoke-WebRequest "$BaseUrl/api/products/$id" -SkipHttpErrorCheck
    Assert-True ($missing.StatusCode -eq 404) 'Deleted product must return 404'
    $id = $null
    $docs = Invoke-RestMethod "$BaseUrl/v3/api-docs"
    Assert-True ($null -ne $docs.paths.'/api/products') 'OpenAPI paths missing'
    Assert-True ((Invoke-WebRequest "$BaseUrl/swagger-ui/index.html").StatusCode -eq 200) 'Swagger UI unavailable'
    Assert-True ((Invoke-WebRequest "$BaseUrl/").StatusCode -eq 200) 'Frontend unavailable'
    Write-Output 'PASS: create, detail, search, pagination, update, validation, delete, 404, OpenAPI, Swagger UI, frontend'
} finally {
    if ($null -ne $id) { Invoke-RestMethod "$BaseUrl/api/products/$id" -Method Delete | Out-Null }
}
