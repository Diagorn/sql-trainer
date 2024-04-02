const USER = 'user'
const ADMIN_ROLE = 'ADMIN'
const USER_ROLE = 'USER'

export function getUserFioShort() {
    const user = requireUser()
    let fio = `${user.lastName} ${user.firstName.charAt(0)}.`
    if (user.middleName) {
        fio += ` ${user.middleName.charAt(0)}.`
    }
    return fio
}

export function requireUser() {
    const user = JSON.parse(localStorage.getItem(USER))
    if (!user) {
        throw 'Не нашлось информации об авторизованном пользователе'
    }
    return user
}

export function isAuthAsAdmin() {
    const user = requireUser()
    return user.role === ADMIN_ROLE
}

export function isAuthAsUser() {
    const user = requireUser()
    return user.role === USER_ROLE
}