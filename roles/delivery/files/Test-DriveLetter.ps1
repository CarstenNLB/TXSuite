param(
    [string[]]$ValidDriveLetters = @('D','G')
)

$drives = Get-PSDrive -PSProvider FileSystem | Where-Object -Property 'Used' -NE -Value 0

foreach($item in $ValidDriveLetters){
    $drive = $drives | Where-Object -Property Name -EQ -Value $item | Select-Object -ExpandProperty Name
    if($drive){
        "$($drive):"
        exit
    }
}

throw "No drive found. '$($ValidDriveLetters)'"