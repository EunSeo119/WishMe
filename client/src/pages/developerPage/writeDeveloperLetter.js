import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import style from "./writeDeveloperLetter.module.css";
import { Link, useNavigate } from "react-router-dom";  // useNavigate import 추가
import { IoIosArrowBack, IoIosArrowForward } from 'react-icons/io'
// import { reloadData } from '../deskPage/deskPage'; // 경로에 맞게 수정

const WriteDeveloperLetter = () => {
    const { assetSeq } = useParams();
    const [nickname, setNickname] = useState("");
    const [content, setContent] = useState("");
    const [isPublic, setIsPublic] = useState(true);
    const [showModal, setShowModal] = useState(false);
    const [selectedButton, setSelectedButton] = useState('public'); // 'public' 또는 'private' 값을 가질 수 있음
    const SERVER_URL = process.env.REACT_APP_SERVER_URL;

    const navigate = useNavigate();

    const handleSave = async () => {

        console.log(isPublic);

        try {
            const data = {
                assetSeq: Number(assetSeq),
                nickname: nickname,
                content: content,
                isPublic: isPublic ?  1 : 0,
            };

            const AccessToken = localStorage.getItem("AccessToken"); // 토큰 값을 가져오는 코드
            const headers = {};

            if (AccessToken) {
                headers.Authorization = `Bearer ${AccessToken}`;
            }

            const response = await axios({
                method: "post",
                url: `${SERVER_URL}/api/developer/letter/write`,
                headers,
                data: data
            });

            alert("응원이 성공적으로 등록되었습니다.");
            return response.data; // API 응답 데이터 반환
        } catch (error) {
            alert("응원 등록에 실패했습니다.");
            console.error("응원 등록 에러", error);
            throw error; // 예외를 다시 던져서 상위에서 처리할 수 있도록 함
        }
    };

    const handleModalConfirm = async () => {
        try {
            await handleSave(); // handleSave 함수가 비동기 함수로 가정
            setShowModal(false);
            navigate(`/developer`);
        } catch (error) {
            // handleSave 함수에서 예외 처리를 하고 있다면 이 곳에서 추가 처리
            console.error('handleSave 함수에서 오류 발생:', error);
        }
    };



    const handleModalCancel = () => {
        setShowModal(false);
    };

    const handleSubmit = () => {
        if (nickname.trim() === "") {
            alert("닉네임을 입력해주세요.");
        }
        else if (content.trim() === "") {
            alert("내용을 입력해주세요.");
        }
        else {
            setShowModal(true);
        }
    };

    const closeModal = () => {
        setShowModal(false);
    };

    return (
        <div className={style.body}>
            <div className={style.navigation}>
                <IoIosArrowBack />
                <Link to={`/developer/selectAsset`} className={style.backLink}>이전으로</Link>
            </div>
            <div className={style.title}>응원의 말을 남겨주세요!</div>

            <div className={style.nickname}>
                <input
                    className={style.nicknameInput}
                    type="text"
                    id="nickname"
                    value={nickname}
                    placeholder="닉네임을 입력해주세요."
                    onChange={e => setNickname(e.target.value)}
                />
            </div>

            <div className={style.letterImg}>
                <img
                    crossOrigin="anonymous"
                    src="https://wishme-bichnali.s3.ap-northeast-2.amazonaws.com/letter/clovaLetter.png"
                />
                <textarea
                    className={style.contentTextarea}
                    placeholder="응원의 글을 적어주세요."
                    value={content}
                    onChange={(e) => setContent(e.target.value)}
                />{' '}
                <div className={style.radioGroup}>
                    <div className={selectedButton === 'public' ? style.selectedButton : style.radioItem}>
                        <label>
                            <input
                                type="radio"
                                name="visibility"
                                checked={isPublic}
                                onClick={() => { setIsPublic(true); setSelectedButton('public'); }}
                            />
                            전체공개
                        </label>
                    </div>
                    <div className={selectedButton === 'private' ? style.selectedButton : style.radioItem}>
                        <label >
                            <input
                                type="radio"
                                name="visibility"
                                checked={!isPublic}
                                onClick={() => { setIsPublic(false); setSelectedButton('private'); }}
                            />
                            비공개
                        </label>
                    </div>
                </div>
            </div>
            <div className={style.btn}>
                <button className={style.submitButton} onClick={handleSubmit}>응원 또는 문의 남기기</button>
            </div>

            {showModal && (
                <Modal closeModal={closeModal} handleModalConfirm={handleModalConfirm} />
            )}
        </div>
    );
};

const Modal = ({ closeModal, handleModalConfirm }) => {
    return (
        <div className={style.modalOverlay}>
            <div className={style.modalContent}>
                <p>등록하면 수정할 수 없습니다.</p>
                <div className={style.modalButtons}>
                    <button onClick={closeModal} className={style.modalButtonBack}>뒤로</button>
                    <button className={style.modalButtonSave} onClick={() => {
                        handleModalConfirm();
                    }} >저장</button>
                </div>
            </div>
        </div>
    );
};

export default WriteDeveloperLetter;
