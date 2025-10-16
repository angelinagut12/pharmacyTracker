# Inventory CLI (File Version) – Java 17

A simple console app that manages products stored in a local CSV file. No database required.

## Run it
1) Open the folder in your IDE (IntelliJ / VS Code / Eclipse).
2) Build the Maven project (no external dependencies).
3) Run `app.App` (main class).

## Data
- App reads/writes `data/products.csv` automatically.
- You can open the CSV in Excel if you want to see/edit data.

## Features
- List products
- Add product
- Update quantity
- Delete product
- Low-stock report

## Next steps (optional)
- Input validation and nicer menus
- Export low-stock report to CSV file
- Switch to MySQL later by replacing `ProductFileStore` with a JDBC DAO
