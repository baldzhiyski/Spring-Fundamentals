separator();
function printStars(count){
    console.log("*".repeat(count))
}
printStars(10);
function separator(){
    console.log("..................................................................")
}
separator();

// Object in js
let person = {
    firstName: "John",
    age: 50,
    lastName: "Bonny",
    document: {
        id: 12,
        name: "CardId"
    }
}
let keys = Object.keys(person);
console.log(keys);

console.log(person.document.name)

