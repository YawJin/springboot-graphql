@echo off
echo Script start

set inputFilePath=".\dataSource.txt"
set outputFilePath=".\temporary.sql"
set sqlCommandPath="C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe"

echo CREATE DATABASE IF NOT EXISTS springbootgraphql; >> %outputFilePath%
echo. >> %outputFilePath%
echo USE springbootgraphql; >> %outputFilePath%
echo. >> %outputFilePath%
echo CREATE TABLE IF NOT EXISTS transaction ( >> %outputFilePath%
echo id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, >> %outputFilePath%
echo account_number VARCHAR(10) NOT NULL, >> %outputFilePath%
echo amount DECIMAL(15,2) NOT NULL, >> %outputFilePath%
echo description VARCHAR(50) NOT NULL, >> %outputFilePath%
echo date DATETIME NOT NULL, >> %outputFilePath%
echo customer_id INT NOT NULL >> %outputFilePath%
echo ); >> %outputFilePath%
echo. >> %outputFilePath%
echo INSERT INTO >> %outputFilePath%
echo transaction(account_number,amount,description,date,customer_id) >> %outputFilePath%
echo VALUES >> %outputFilePath%

rem Count the number of lines for inputFile
for /f "usebackq" %%b in (`type %inputFilePath% ^| find "" /v /c`) do (
  set lineCount=%%b
)

rem Split string by pipe character
for /f "tokens=1,2,3,4,5,6 delims=|" %%a in ('findstr /n .* %inputFilePath%') do (
  for /f "tokens=1,2 delims=:" %%g in ("%%a") do (
    if %%g==1 (
      rem Do nothing
    ) else (
      if %%g==%lineCount% (
        echo ^('%%h',%%b,'%%c','%%d %%e',%%f^); >> %outputFilePath%
      ) else (
        echo ^('%%h',%%b,'%%c','%%d %%e',%%f^), >> %outputFilePath%
      )
    )
  )
)

%sqlCommandPath% --user=root --password -s < %outputFilePath%

del %outputFilePath%

echo Script done