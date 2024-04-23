import axios from "axios";
import authHeader from "@/services/auth-header.js";

const API_URL = 'http://localhost:8080/api/v1/user'

class UserService {

    getUsers() {
        return axios.get(API_URL, {
            headers: authHeader()
        })
            .then(res => {
                return res.data
            })
    }

    getUserById(userId) {
        return axios.get(API_URL + '/' + userId, {
            headers: authHeader()
        }).then(res => res.data)
    }
}

export default new UserService()