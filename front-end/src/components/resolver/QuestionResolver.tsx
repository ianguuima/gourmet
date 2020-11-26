import React, { useEffect, useState } from 'react';
import { Button } from '../Button/Button';
import { Container } from '../Container/Container';
import Dish from '../../interfaces/Dish';
import { getFoodByIngredients, getInitialResults } from '../../api/requests';

const QuestionResolver: React.FC = () => {

    const [ingredients, setIngredients] = useState<string[]>()
    const [dishFound, setDishFound] = useState<Dish | undefined | null>()

    const [current, setCurrent] = useState<number>(0)
    const [chosenIngredients, setChosenIngredients] = useState<string[]>([])

    useEffect(() => {
        getInitialResults().then((result: Dish[]) => {
            const dishIngredients: string[] = []
            result.map(
                m => m.ingredients.forEach(i => !dishIngredients.includes(i) ? dishIngredients.push(i) : {})
            )
            setIngredients(dishIngredients)
        })
    }, [])

    function notFound() {
        return <p>We don't know what you like :(</p>
    }

    function onYes(item : string) {
        setCurrent(current + 1)
        validateIngredients()
        setChosenIngredients([...chosenIngredients, item])
    }

    function onNo() {
        setCurrent(current + 1)
        validateIngredients()
    }

    function validateIngredients() {
        if ((current + 1) >= ingredients!.length) {
            getFoodByIngredients(chosenIngredients!.join("%20")).then((dish: Dish) => setDishFound(dish))
        }
    }

    function restart() {
        setChosenIngredients([])
        setCurrent(0)
        setDishFound(undefined)
    }

    function showThinkingFood() {
        return (
            <>
                <h3>The food you are thinking is {dishFound!.name}</h3>
                <Button color="#118f26" darkColor="#0c611a" onClick={restart}>Try Again!</Button>
            </>
        )
    }


    function showDialog() {
        if (dishFound === null) {
            return notFound()
        }

        if (dishFound === undefined) {
            return <><h3>Does the food you are thinking have {ingredients![current]}?</h3>
                <Container>
                    <Button color="#16a62e" darkColor="#118f26" onClick={() => onYes(ingredients![current])}>YES</Button>
                    <Button color="#f22727" darkColor="#ad1c1c" onClick={onNo}>NO</Button>
                </Container></>
        }

        return showThinkingFood()
    }

    return ingredients === undefined ? notFound() : showDialog()
}

export default QuestionResolver