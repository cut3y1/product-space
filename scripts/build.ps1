#requires -Version 7.0
$ErrorActionPreference = 'Stop'
Push-Location (Join-Path $PSScriptRoot '..')
try {
    mvn -B -ntp '-Dmaven.repo.local=.maven-repository' clean package
    if ($LASTEXITCODE -ne 0) { throw 'Maven build failed' }
    New-Item -ItemType Directory -Force delivery | Out-Null
    Copy-Item target/product-crud.jar delivery/product-crud.jar
    # Keep editable source separate and deliver a self-contained HTML file.
    $html = Get-Content src/main/resources/static/index.html -Raw
    $css = Get-Content src/main/resources/static/css/app.css -Raw
    $js = Get-Content src/main/resources/static/js/app.js -Raw
    $html = $html.Replace('<link rel="stylesheet" href="/css/app.css">', "<style>`n$css`n</style>")
    $html = $html.Replace('<script src="/js/app.js" defer></script>', "<script>`n$js`n</script>")
    Set-Content delivery/index.html $html -Encoding utf8
    Copy-Item docs/openapi.json delivery/openapi.json
    Copy-Item database/init.sql delivery/init.sql
    Copy-Item docs/SUBMISSION.md delivery/README.md
    Copy-Item scripts/run-delivery.ps1 delivery/start.ps1
    Write-Output 'Build complete: delivery/'
} finally { Pop-Location }
