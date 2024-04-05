import {createStore} from "vuex";
import {auth} from "@/store/auth.module.js";
import {task} from "@/store/task.module.js";

const store = createStore({
    modules: {
        auth,
        task,
    }
})

export default store