import React, { useState } from 'react';
import { useNavigate } from 'react-router';
import style from './Header.module.css';
import toast, { Toaster } from 'react-hot-toast'

const Header = () => {
    const navigate = useNavigate();

    const [isSidebarOpen, setIsSidebarOpen] = useState(false);
    const toggleSidebar = () => {
        setIsSidebarOpen(!isSidebarOpen);
    }

    return (
        <div className={style.header}>
            <Toaster />
            {/* <div className="menuIcon" onClick={toggleSidebar}> */}
            <div onClick={toggleSidebar}>
                <img src="/assets/Menu.png" className={style.menuIcon} />
            </div>
            <div className={`${style.sidebar} ${isSidebarOpen ? style.open : ''}`}>
                <div className={style.sideName}>TEST님</div>
                <div>반갑습니다!</div>
            </div>
            {isSidebarOpen && (
                <div className={style.overlay} onClick={toggleSidebar}></div>
            )}
        </div>
    );
}

export default Header;
