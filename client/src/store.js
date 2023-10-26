// const { configureStore } = require("@reduxjs/toolkit");
// const reducer = require("./reducers");

// // middleware
// const firstMiddleware = () => (next) => (action) => {
// 	console.log("log:", action);
// 	next(action);
// };

// // store
// const store = configureStore({
// 	reducer,
// 	middleware: (getDefaultMiddleware) => getDefaultMiddleware().concat(firstMiddleware),
// 	devTools: process.env.NODE_ENV !== "production",
// });

// module.exports = store;

// middleware -> dispatch -> store 순