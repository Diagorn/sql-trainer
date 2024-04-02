import axios from "axios";

const API_URL = 'http://localhost:8080/api/v1/auth/'

class AuthService {
    login(user) {
        return axios
            .post(API_URL + 'login', {
                email: user.email,
                password: user.password,
            }).then(response => {
                console.log(response.data)
                if (response.data.token) {
                    localStorage.setItem('user', JSON.stringify(response.data))
                }

                return response.data
            })
    }

    logout() {
        localStorage.removeItem('user')
    }

    register(user) {
        return axios.post(API_URL + 'register', {
            email: user.email,
            password: user.password,
            firstName: user.firstName,
            lastName: user.lastName,
            middleName: user.middleName,
        })
    }
}

export default new AuthService()