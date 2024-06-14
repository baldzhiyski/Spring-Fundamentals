let reloadBooksButton = document.getElementById('reloadBooks');

reloadBooksButton.addEventListener('click', reloadBooks)

function reloadBooks() {

    let booksContainer = document.getElementById('books-container');
    booksContainer.innerHTML = ''

    fetch('http://localhost:8080/api/books')
        .then(response => response.json())
        .then(json => json.forEach(book => {

            let bookRow = document.createElement('tr')

            let titleCol = document.createElement('td')
            let authorCol	= document.createElement('td')
            let isbnCol	= document.createElement('td')
            let actionCol= document.createElement('td')

            titleCol.textContent = book.title
            authorCol.textContent = book.author.name
            isbnCol.textContent = book.isbn

            let deleteBookBtn = document.createElement('button')
            deleteBookBtn.innerHTML = 'DELETE'
            deleteBookBtn.dataset.id = book.id
            deleteBookBtn.addEventListener('click', deleteBtnClicked)

            actionCol.appendChild(deleteBookBtn)

            bookRow.appendChild(titleCol)
            bookRow.appendChild(authorCol)
            bookRow.appendChild(isbnCol)
            bookRow.appendChild(actionCol)

            booksContainer.append(bookRow)
        }))

}

function deleteBtnClicked(event) {

    let bookId = event.target.dataset.id;
    let requestOptions = {
        method: 'DELETE'
    }

    fetch(`http://localhost:8080/api/books/${bookId}`, requestOptions)
        .then(_ => reloadBooks())
        .catch(error => console.log('error', error))
}

let form = document.getElementById('bookForm');
form.addEventListener('submit', submitForm);

function submitForm(event) {
    event.preventDefault();

    let title = document.getElementById('title').value;
    let authorName = document.getElementById('author').value;
    let isbn = document.getElementById('isbn').value;

    let authorDTO = {
        name: authorName
    };

    let bookDTO = {
        title: title,
        isbn: isbn,
        author: authorDTO
    };

    let requestOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(bookDTO)
    };

    fetch('http://localhost:8080/api/books', requestOptions)
        .then(response => {
            if (!response.ok) {
                return response.text().then(errorMessage => {
                    throw new Error(errorMessage);
                });
            }
            reloadBooks(); // Reload books after successful creation
            form.reset(); // Reset form fields
        })
        .catch(error => {
            alert('Error creating book: ' + error.message);
        });
}