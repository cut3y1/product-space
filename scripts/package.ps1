#requires -Version 7.0
$ErrorActionPreference = 'Stop'
Push-Location (Join-Path $PSScriptRoot '..')
try {
    & ./scripts/build.ps1
    New-Item -ItemType Directory -Force dist | Out-Null
    $files = 'product-crud.jar','openapi.json','index.html','init.sql','README.md','start.ps1' | ForEach-Object { Join-Path delivery $_ }
    Compress-Archive -LiteralPath $files -DestinationPath dist/product-crud-submission.zip -Force
    Write-Output 'Submission ready: dist/product-crud-submission.zip'
} finally { Pop-Location }
