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
$jar = Join-Path $PSScriptRoot '../delivery/product-crud.jar'
if (-not (Test-Path -LiteralPath $jar)) { throw 'Build first: pwsh -File ./scripts/build.ps1' }
java -jar $jar

