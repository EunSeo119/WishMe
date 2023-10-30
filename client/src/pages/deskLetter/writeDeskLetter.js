import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import style from "./writeDeskLetter.module.css";
import { Link, useNavigate } from "react-router-dom";  // useNavigate import 추가
import { IoIosArrowBack, IoIosArrowForward } from 'react-icons/io'

const WriteDeskLetter = () => {
    const { assetSeq, deskUuid } = useParams();
    const [nickname, setNickname] = useState("");
    const [content, setContent] = useState("");
    const [isPublic, setIsPublic] = useState(true);
    const [showModal, setShowModal] = useState(false);
    const [selectedButton, setSelectedButton] = useState('public'); // 'public' 또는 'private' 값을 가질 수 있음
    // const { deskUuid } = useParams();

    const navigate = useNavigate();

    const handleSave = () => {
        const data = {
            assetSeq: Number(assetSeq),
            fromUserNickname: nickname,
            content: content,
            isPublic: isPublic ? 1 : 0,
            toUserUuid: deskUuid // 향후 필요에 따라 변경하시면 됩니다.
        };

        const AccessToken = localStorage.getItem("AccessToken"); // 토큰 값을 가져오는 코드

        axios({
            method: "post",
            url: 'http://localhost:8080/api/my/letter/write',
            headers: {
                Authorization: `Bearer ${AccessToken}`,
            },
            data: data
        })
            .then(response => {
                alert("응원이 성공적으로 등록되었습니다.");
            })
            .catch(error => {
                alert("응원 등록에 실패했습니다.");
                console.error("응원 등록 에러", error);
            });
    };

    const handleModalConfirm = () => {
        handleSave();
        setShowModal(false);
        // navigate('/desk');
        navigate(`/desk/${deskUuid}`);
    };

    const handleModalCancel = () => {
        setShowModal(false);
    };

    const handleSubmit = () => {
        setShowModal(true);
    };

    const closeModal = () => {
        setShowModal(false);
    };

    return (
        <div className={style.body}>
            <div className={style.navigation}>
                <IoIosArrowBack />
                <Link to="/desk/selectAsset" className={style.backLink}>이전으로</Link>
            </div>
            <p className={style.title}>응원의 말을 남겨주세요!</p>

            <div >
                <input
                    className={style.nicknameInput}
                    placeholder="닉네임을 입력해주세요."
                    type="text"
                    value={nickname}
                    onChange={e => setNickname(e.target.value)}
                />
            </div>

            <div className={style.letterContainer}>

                <textarea
                    className={style.contentTextarea}
                    placeholder="응원의 글을 적어주세요."
                    value={content}
                    onChange={e => setContent(e.target.value)}
                />

                <div className={style.radioGroup}>
                    <div className={selectedButton === 'public' ? style.selectedButton : style.radioItem}>
                        <label>
                            <input
                                type="radio"
                                name="visibility"
                                checked={isPublic}
                                onChange={() => { setIsPublic(true); setSelectedButton('public'); }}
                            />
                            공개
                        </label>
                    </div>
                    <div className={selectedButton === 'private' ? style.selectedButton : style.radioItem}>
                        <label >
                            <input
                                type="radio"
                                name="visibility"
                                checked={!isPublic}
                                onChange={() => { setIsPublic(false); setSelectedButton('private'); }}
                            />
                            비공개
                        </label>
                    </div>
                </div>
            </div>
            <div className={style.btn}>
                <button className={style.submitButton} onClick={handleSubmit}>응원 남기기</button>
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
                    <button onClick={closeModal}>뒤로</button>
                    <button onClick={() => {
                        handleModalConfirm();
                    }}>저장</button>
                </div>
            </div>
        </div>
    );
};

export default WriteDeskLetter;
