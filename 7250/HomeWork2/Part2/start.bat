start mongod
start mongoimport
FOR %%i IN (C:\Users\yancw\Desktop\NYSE\*.csv) DO mongoimport --db mydb --collection NYSE --type csv --headerline --file %%i