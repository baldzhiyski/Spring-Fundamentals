function personalBMI(name,age,weight,height){
    let calculateBMI =Math.round(weight/(height/100 * height/100));

    let status = () => {
        if(calculateBMI < 18.5){
            return "underweight";
        }
        if(calculateBMI < 25){
            return "normal";
        }
        if(calculateBMI < 30){
            return "overweight";
        }

        return "obese";
    }

    let result =  {
        name: name,
        personalInfo : {
            age: age,
            weight:weight,
            height:height
        },
        BMI: calculateBMI,
        status:status()
    }

    if(status() === `obese`){}
    result.recomendation = "admission required"

    console.log(result);

}

personalBMI("Honey Boo Boo", 24,12345,110)