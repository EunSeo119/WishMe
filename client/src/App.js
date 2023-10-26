import React from "react";
import { Route, Routes } from "react-router-dom";
import MainPage from "./pages/mainPage";
import DeskPage from "./pages/deskPage";
import SchoolPage from "./pages/schoolPage";

function App() {
  return (
      <Routes>
        <Route path="/" element={<MainPage />}></Route>
        <Route path="/desk" element={<DeskPage />}></Route>
        <Route path="/school" element={<SchoolPage />}></Route>
      </Routes>
  );
}

export default App;
