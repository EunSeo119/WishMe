import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App";
import { BrowserRouter } from "react-router-dom";
import { Provider } from "react-redux";
import store from "./store";
import "./fonts/font.css";

const root = ReactDOM.createRoot(document.getElementById("root"));

root.render(
    <BrowserRouter>
        <App />
        {/* <!-- Google tag (gtag.js) --> */}

        <script
            async
            src="https://www.googletagmanager.com/gtag/js?id=G-1BFGW7JD08"
        ></script>
        <script>
            window.dataLayer = window.dataLayer || []; function gtag()
            {dataLayer.push(arguments)}
            gtag('js', new Date()); gtag('config', 'G-1BFGW7JD08');
        </script>
    </BrowserRouter>
);
