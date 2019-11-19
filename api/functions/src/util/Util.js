// maps the firebase result to an array if applicable
exports.mapArray = (result) => {
    const docs = result.docs || result;
    const values = docs.map(doc => doc.data());
    if (values.length === 0) { return [] }
    return values
};

// Few wrappers for handling and unwrapping promises safely
exports.r = fn => (req, res, next) => {
    Promise.resolve(fn(req, res, next)).catch(next);
};

exports.w = promise => (
    promise.then(result => ({ result, error: null })).catch(error => {
        console.log(error.stack);
        return { error, result: null }
    })
);