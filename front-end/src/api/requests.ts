import api from './axios';

export const getInitialResults = async () => {
    const response = await api.get("/dish?page=0&size=30")
    return response.data
}

export const findDish = async (id : number) => {
    const response = await api.get(`/dish/${id}`)

    return response.data
}

export const getFoodByIngredients = async (term : string) => {
    try {
        const response = await api.get(`/dish/search?term=${term}`)
        const items : string[] = response.data
        return findDish(Number.parseInt(items[0].split(":")[1]))
    } catch (err) {
        console.log(err)
        return null
    }

   
}