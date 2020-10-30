@if "%DEBUG%" == "" @echo off

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

:begin
@rem Determine what directory it is in.
set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.\

"groovy.bat -cp %DIRNAME%\lib\jmatharray.jar;%DIRNAME%\lib\jmathplot.jar;%DIRNAME%\src" %*

@rem End local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" endlocal
