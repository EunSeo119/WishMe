import React from 'react'
import { Route, Routes } from 'react-router-dom'
import MainPage from './pages/mainPage'
import DeskPage from './pages/deskPage'
import School from './pages/schoolPage'

function App() {
  return (
    <>
      <Routes>
        <Route path="/" element={<MainPage />}></Route>
        <Route path="/desk" element={<DeskPage />}></Route>
        <Route path="/school" element={<School />}></Route>
      </Routes>
    </>
  )
}

export default App
