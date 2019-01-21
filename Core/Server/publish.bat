dotnet publish --configuration Release --output Z:\Servers\Kyrillos\Trophnus\ChatterServer
robocopy /is /R:1 sql.sccfg Z:\Servers\Kyrillos\Trophnus\ChatterServer\sql.sccfg
robocopy /is /E /R:1 Content Z:\Servers\Kyrillos\Trophnus\ChatterServer\Content
robocopy /is /E /R:1  EndpointData Z:\Servers\Kyrillos\Trophnus\ChatterServer\EndpointData 
robocopy /is /E /R:1  UserFiles Z:\Servers\Kyrillos\Trophnus\ChatterServer\UserFiles 
pause