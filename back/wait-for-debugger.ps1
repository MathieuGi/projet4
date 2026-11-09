$port = 5005
$timeout = 60
$timer = [System.Diagnostics.Stopwatch]::StartNew()

while ($timer.Elapsed.TotalSeconds -lt $timeout) {
    $connection = Test-NetConnection localhost -Port $port -WarningAction SilentlyContinue

    if ($connection.TcpTestSucceeded) {
        Write-Host "Debugger port $port is ready."
        exit 0
    }

    Start-Sleep -Milliseconds 500
}

Write-Error "Timeout: debugger port $port was not opened."
exit 1