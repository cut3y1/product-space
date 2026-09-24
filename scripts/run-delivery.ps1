#requires -Version 7.0
param(
    [string]$DatabaseUrl = 'jdbc:mysql://localhost:3307/product_crud?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai',
    [string]$Username = 'root'
)
$ErrorActionPreference = 'Stop'
$env:MYSQL_URL = $DatabaseUrl
$env:MYSQL_USERNAME = $Username
if (-not $env:MYSQL_PASSWORD) {
    $secret = Read-Host 'MySQL password' -AsSecureString
    $env:MYSQL_PASSWORD = [System.Net.NetworkCredential]::new('', $secret).Password
}
java -jar (Join-Path $PSScriptRoot 'product-crud.jar')
if ($LASTEXITCODE -ne 0) { throw "Application exited with code $LASTEXITCODE" }
