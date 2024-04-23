import axios from "axios";
import authHeader from "@/services/auth-header.js";

const API_URL = 'http://localhost:8080/api/v1/solution'

class SolutionService {

    getLatestSolution(taskId) {
        return axios
            .get(API_URL + '/' + taskId, {
                headers: authHeader()
            }).then(response => {
                return response.data
            }).catch(error => null)
    }

    registerNewAttempt(solution) {
        return axios
            .post(API_URL, solution, {
                headers: authHeader(),
            })
            .then(response => {
                return response.data
            })
            .catch(error => console.log(error))
    }
}

export default new SolutionService()