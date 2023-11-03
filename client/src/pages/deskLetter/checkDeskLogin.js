import React, { useState, useEffect } from "react"; // useEffect import 추가
import { Link, useNavigate } from "react-router-dom"; // useNavigate import 추가
import style from "./checkDeskLogin.module.css";
import axios from "axios"; // axios import
import { IoIosArrowBack, IoIosArrowForward } from "react-icons/io";
import { useParams } from "react-router-dom";

const CheckDeskLogin = () => {
    const navigate = useNavigate(); // useNavigate 초기화
    const { deskUuid } = useParams();

    return (
        <div className={style.body}>
            <div className={style.navigation}>
                <IoIosArrowBack className={style.icon} />
                <Link to={`/desk/${deskUuid}`} className={style.backLink}>
                    이전으로
                </Link>
            </div>

            <p className={style.title}>
                로그인을 하지 않더라도
                <br />
                모두에게 응원을 보낼 수 있지만
                <br />
                답장은 받을 수 없어요. 😥
            </p>
            <p className={style.content}>답장을 받으려면 로그인 해주세요!</p>
            <div className={style.btn}>
                <Link to={`/login/${deskUuid}`} className={style.link}>
                    <div className={style.selectBtn}>로그인 하기</div>
                </Link>
                <Link
                    to={`/desk/${deskUuid}/selectAsset`}
                    className={style.link}
                >
                    <div className={style.selectBtn}>다음</div>
                </Link>
            </div>
        </div>
    );
};

export default CheckDeskLogin;
