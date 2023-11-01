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
    const [isLoggedIn, setIsLoggedIn] = useState(false); // 로그인 상태를 나타내는 상태 변수


    const toggleSidebar = () => {
        setIsSidebarOpen(!isSidebarOpen);
    }

    useEffect(() => {
        const AccessToken = localStorage.getItem("AccessToken");
        if (AccessToken != null) {
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
                    if (data.data && data.data.userNickname) {
                        setUserName(data.data.userNickname); // 데이터가 있으면 userName을 상태로 업데이트
                        setIsLoggedIn(true); // 로그인 상태로 설정
                    }

                })
                .catch((error) => {
                    setIsLoggedIn(false); // 에러 시 로그아웃 상태로 설정
                });
        }
        else {
            setIsLoggedIn(false); // AccessToken이 없을 때도 로그아웃 상태로 설정
        }
    }, []); // 빈 배열을 넘겨 한 번만 호출되도록 설정

    // 링크를 클릭하면 해당 페이지로 이동하는 함수
    const handleLinkClick = (path) => {
        navigate(path);
        setIsSidebarOpen(false); // 사이드바를 닫습니다.
    }

    const handleLogout = (path) => {
        localStorage.removeItem("AccessToken"); // AccessToken 삭제
        setUserName(undefined); // userName 상태를 undefined로 설정
        setIsLoggedIn(false);
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
                <div className={style.sideName}>
                    {userName !== undefined ? `${userName} 님 반갑습니다!` : "반갑습니다!"}
                </div>
                <br></br>
                {isLoggedIn ? (
                    <>
                        <div
                            onClick={() => handleLinkClick('/mypage')}
                            style={{
                                cursor: 'pointer',
                                padding: '10px',
                                borderBottom: '1px solid #ccc',
                                fontFamily: 'omyu_pretty'
                            }}
                        >
                            마이페이지
                        </div>
                        <div
                            onClick={() => handleLinkClick('/searchSchool')}
                            style={{
                                cursor: 'pointer',
                                padding: '10px',
                                borderBottom: '1px solid #ccc',
                                fontFamily: 'omyu_pretty'
                            }}
                        >학교 칠판 구경하기</div>
                        <div
                            onClick={() => handleLinkClick('/developer')}
                            style={{
                                cursor: 'pointer',
                                padding: '10px',
                                borderBottom: '1px solid #ccc',
                                fontFamily: 'omyu_pretty'
                            }}
                        >개발자 책상 가기</div>
                        <div
                            onClick={() => handleLogout('/')} // 로그아웃 버튼 클릭 시
                            style={{
                                cursor: 'pointer',
                                padding: '10px',
                                borderBottom: '1px solid #ccc',
                                fontFamily: 'omyu_pretty'
                            }}
                        >로그아웃</div>
                    </>
                ) : (
                    <>
                        <div
                            onClick={() => handleLinkClick('/desk/bf68c8c1-2917-4a3c-b926-fd6d9e7880ab')}
                            style={{
                                cursor: 'pointer',
                                padding: '10px',
                                borderBottom: '1px solid #ccc',
                                fontFamily: 'omyu_pretty'
                            }}
                        >개발자 책상 가기</div>
                        <div
                            onClick={() => handleLinkClick('/searchSchool')}
                            style={{
                                cursor: 'pointer',
                                padding: '10px',
                                borderBottom: '1px solid #ccc',
                                fontFamily: 'omyu_pretty'
                            }}
                        >학교 칠판 구경하기</div>
                        <div
                            onClick={() => handleLinkClick('/')}
                            style={{
                                cursor: 'pointer',
                                padding: '10px',
                                borderBottom: '1px solid #ccc',
                                fontFamily: 'omyu_pretty'
                            }}
                        >
                            로그인하기
                        </div>
                    </>
                )}

                <div className={style.copyRight} style={{ position: 'fixed', bottom: '0',   fontFamily: 'omyu_pretty' }}>
                    <div style={{width : "100%"}}>copyright(c) 빛나리</div>
                    <div>instagram <a href="https://www.instagram.com/wish_me_1116/" target="_blank" rel="noopener noreferrer">@wish_me_1116</a></div>
                    <div  className={style.copyRight} style={{ fontSize: '12px', color: '#ccc' ,   fontFamily: 'omyu_pretty'}} >designed by manshagraphics<br></br>from Flaticon </div>
                </div>


            </div>




            {isSidebarOpen && (
                <div className={style.overlay} onClick={toggleSidebar}></div>
            )}
        </div>
    );
}

export default Header;
