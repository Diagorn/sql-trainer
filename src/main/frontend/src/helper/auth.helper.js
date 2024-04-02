export function getUserFioShort(user) {
    let fio = `${user.lastName} ${user.firstName.charAt(0)}.`
    if (user.middleName) {
        fio += ` ${user.middleName.charAt(0)}.`
    }
    return fio
}