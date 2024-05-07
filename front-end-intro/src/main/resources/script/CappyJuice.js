function cappyJuice(juices) {
    let juiceQuantities = new Map();
    let juiceBottles = new Map();

    for (let juice of juices) {
        let [name, quantity] = juice.split(" => ");
        quantity = parseInt(quantity);

        if (!juiceQuantities.has(name)) {
            juiceQuantities.set(name, 0);
        }

        let currentQuantity = juiceQuantities.get(name);
        let totalQuantity = currentQuantity + quantity;
        let bottlesProduced = Math.floor(totalQuantity / 1000);
        let remainingQuantity = totalQuantity -(bottlesProduced* 1000);

        juiceQuantities.set(name, remainingQuantity);

        if (bottlesProduced > 0) {
            if(!juiceBottles.has(name)){
                juiceBottles.set(name,bottlesProduced)
            }else{
                juiceBottles.set(name,juiceBottles.get(name) + bottlesProduced)
            }
        }
    }

    for (let entry of juiceBottles.entries()) {
        console.log(`${entry[0]} => ${entry[1]}`);
    }
}

cappyJuice(['Kiwi => 234',
    'Pear => 2345',
    'Watermelon => 3456',
    'Kiwi => 4567',
    'Pear => 5678',
    'Watermelon => 6789'])