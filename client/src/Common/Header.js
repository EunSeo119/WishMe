import React, {useState} from 'react';
import {useNavigate} from 'react-router';
import style from './Header.module.css';
import toast, {Toaster} from 'react-hot-toast';

const Header = () => {
    const navigate = useNavigate();

    const [isSidebarOpen, setIsSidebarOpen] = useState(false);
    const toggleSidebar = () => {
        setIsSidebarOpen(!isSidebarOpen);
    }

    // 링크를 클릭하면 해당 페이지로 이동하는 함수
    const handleLinkClick = (path) => {
        navigate(path);
        setIsSidebarOpen(false); // 사이드바를 닫습니다.
    }

    return (
        <div className={style.header}>
            <Toaster/>
            <div onClick={toggleSidebar}>
                <img src="/assets/Menu.png" className={style.menuIcon}/>
            </div>
            <div className={`${style.sidebar} ${isSidebarOpen ? style.open : ''}`}>
                <div className={style.sideName}>TEST님</div>
                <div>반갑습니다!</div>

                <br></br>
                <div
                    onClick={() => handleLinkClick('/')}
                    style={{
                        cursor: 'pointer',
                        padding: '10px',
                        borderBottom: '1px solid #ccc',
                    }}
                >
                    홈
                </div>
                <div
                    onClick={() => handleLinkClick('/mypage')}
                    style={{
                        cursor: 'pointer',
                        padding: '10px',
                        borderBottom: '1px solid #ccc',
                    }}
                >
                    마이페이지 </div>
                <div
                    onClick={() => handleLinkClick('/')}
                    style={{
                        cursor: 'pointer',
                        padding: '10px',
                        borderBottom: '1px solid #ccc',
                    }}
                >개발자 책상 가기</div>
                <div
                    onClick={() => handleLinkClick('/')}
                    style={{
                        cursor: 'pointer',
                        padding: '10px',
                        borderBottom: '1px solid #ccc',
                    }}
                >로그아웃</div>

            </div>
            {isSidebarOpen && (
                <div className={style.overlay} onClick={toggleSidebar}></div>
            )}
        </div>
    );
}

export default Header;
