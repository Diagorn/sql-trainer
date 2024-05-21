export function getTaskMark(userExecutionTime, taskExecutionTime, lengthPercent, taskRating) {
    const timePercent = _getPercent(userExecutionTime, taskExecutionTime)
    console.log(timePercent)
    const ratingCoef = _getRatingCoef(lengthPercent, timePercent)
    console.log(ratingCoef)
    console.log(ratingCoef * taskRating)
    return ratingCoef * taskRating
}

function _getRatingCoef(lengthPercent, timePercent) {
    if (lengthPercent < 30) {
        if (timePercent < 30) {
            return 0.1
        } else if (timePercent < 60) {
            return 0.3
        } else if (timePercent < 90) {
            return 0.5
        } else {
            return 0.6
        }
    } else if (lengthPercent < 60) {
        if (timePercent < 30) {
            return 0.2
        } else if (timePercent < 60) {
            return 0.4
        } else if (timePercent < 90) {
            return 0.6
        } else {
            return 0.8
        }
    } else if (lengthPercent < 90) {
        if (timePercent < 30) {
            return 0.3
        } else if (timePercent < 60) {
            return 0.5
        } else if (timePercent < 90) {
            return 0.7
        } else {
            return 0.9
        }
    } else {
        if (timePercent < 30) {
            return 0.4
        } else if (timePercent < 60) {
            return 0.6
        } else if (timePercent < 90) {
            return 0.8
        } else {
            return 1
        }
    }
}

function _getPercent(num1, num2) {
    if (num2 === 0) {
        return NaN
    }

    if (num1 < num2) {
        return 100
    }

    const result = (num2 / num1) * 100;
    return parseFloat(result.toFixed(2));
}