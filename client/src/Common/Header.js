import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router';
import style from './Header.module.css';
import toast, { Toaster } from 'react-hot-toast';
import axios from "axios";

const Header = () => {
    const navigate = useNavigate();
    const SERVER_URL = process.env.REACT_APP_SERVER_URL;

    const [isSidebarOpen, setIsSidebarOpen] = useState(false);
    const [userName, setUserName] = useState(""); // 상태로 userName 관리

    const toggleSidebar = () => {
        setIsSidebarOpen(!isSidebarOpen);
    }

    useEffect(() => {
        const AccessToken = localStorage.getItem("AccessToken");
        if (AccessToken) {
            const headers = {
                Authorization: `Bearer ${AccessToken}`
            };

            axios({
                method: "get",
                url: `${SERVER_URL}/api/users`,
                headers,
            })
                .then((response) => {
                    const data = response.data;
                    console.log(data);
                    if (data && data.userName) {
                        setUserName(data.userName); // 데이터가 있으면 userName을 상태로 업데이트
                    }
                })
                .catch((error) => {
                    console.log(SERVER_URL);
                    console.error("API 요청 중 오류 발생:", error);
                });
        }
    }, []); // 빈 배열을 넘겨 한 번만 호출되도록 설정

    // 링크를 클릭하면 해당 페이지로 이동하는 함수
    const handleLinkClick = (path) => {
        navigate(path);
        setIsSidebarOpen(false); // 사이드바를 닫습니다.
    }

    const handleLogout = (path) => {
        localStorage.removeItem("AccessToken"); // AccessToken 삭제
        navigate(path); // 로그아웃 후 이동할 페이지 경로
        setIsSidebarOpen(false); // 사이드바를 닫습니다.
    }


    return (
        <div className={style.header}>
            <Toaster />
            <div onClick={toggleSidebar}>
                <img src="/assets/Menu.png" className={style.menuIcon} />
            </div>
            <div className={`${style.sidebar} ${isSidebarOpen ? style.open : ''}`}>
                <div className={style.sideName}> {userName} </div>
                <div>반갑습니다!</div>

                <br></br>
                {userName ? (
                    <>
                        <div
                            onClick={() => handleLinkClick('/mypage')}
                            style={{
                                cursor: 'pointer',
                                padding: '10px',
                                borderBottom: '1px solid #ccc',
                            }}
                        >
                            마이페이지
                        </div>
                        <div
                            onClick={() => handleLogout('/')} // 로그아웃 버튼 클릭 시
                            style={{
                                cursor: 'pointer',
                                padding: '10px',
                                borderBottom: '1px solid #ccc',
                            }}
                        >로그아웃</div>
                    </>
                ) : (
                    <>
                        <div
                            onClick={() => handleLinkClick('/')}
                            style={{
                                cursor: 'pointer',
                                padding: '10px',
                                borderBottom: '1px solid #ccc',
                            }}
                        >
                            로그인하기
                        </div>
                        <div
                            onClick={() => handleLinkClick('/developer-desk')}
                            style={{
                                cursor: 'pointer',
                                padding: '10px',
                                borderBottom: '1px solid #ccc',
                            }}
                        >개발자 책상 가기</div>
                    </>
                )}
            </div>
            {isSidebarOpen && (
                <div className={style.overlay} onClick={toggleSidebar}></div>
            )}
        </div>
    );
}

export default Header;
