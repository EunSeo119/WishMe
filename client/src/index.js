import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App";
import { BrowserRouter } from "react-router-dom";
import "./fonts/font.css";
import RouteChangeTracker from "./RouteChangeTracker";
import Hotjar from "./Hotjar";

const root = ReactDOM.createRoot(document.getElementById("root"));

root.render(
    <BrowserRouter>
        <RouteChangeTracker />
        <Hotjar />
        <App />
    </BrowserRouter>
);
