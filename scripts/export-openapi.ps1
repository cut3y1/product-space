#requires -Version 7.0
param([string]$BaseUrl = 'http://localhost:8080')
$ErrorActionPreference = 'Stop'
$response = Invoke-WebRequest "$BaseUrl/v3/api-docs"
$document = $response.Content | ConvertFrom-Json
if (-not $document.paths.'/api/products') { throw 'Expected product API not found' }
Set-Content (Join-Path $PSScriptRoot '../docs/openapi.json') $response.Content -Encoding utf8
Write-Output 'Updated docs/openapi.json'
