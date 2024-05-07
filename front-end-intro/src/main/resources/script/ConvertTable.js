function convertTable(args) {
    let tableHTML = "<table>\n"; // Start the table HTML

    args.forEach(person => {
        let name = person.name;
        let position = person.position;
        let salary = person.salary;
        // Concatenate each table row to the tableHTML string, with appropriate indentation
        tableHTML += `  <tr>\n    <td>${name}</td>\n    <td>${position}</td>\n    <td>${salary}</td>\n  </tr>\n`;
    });

    tableHTML += "</table>"; // End the table HTML
    console.log(tableHTML); // Print the entire table HTML
}

convertTable([
    {"name":"Pesho","position":"Promenliva","salary":100000},
    {"name":"Teo","position":"Lecturer","salary":1000},
    {"name":"Georgi","position":"Lecturer","salary":1000}
]);
