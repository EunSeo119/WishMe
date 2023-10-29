import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import styles from "./writeDeskLetter.module.css";
import styleApp from '../../app.module.css'
import { Link, useNavigate } from "react-router-dom";  // useNavigate import 추가

const WriteDeskLetter = () => {
    const { assetSeq } = useParams();
    const [nickname, setNickname] = useState("");
    const [content, setContent] = useState("");
    const [isPublic, setIsPublic] = useState(true);
    const [showModal, setShowModal] = useState(false);
    const navigate = useNavigate();

    const handleSave = () => {
        const data = {
            assetSeq: Number(assetSeq),
            fromUserNickname: nickname,
            content: content,
            isPublic: isPublic ? 1 : 0,
            toUserSeq: localStorage.getItem("deskUserSeq") // 향후 필요에 따라 변경하시면 됩니다.
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
        navigate('/desk');
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
        <div className={styleApp.app}>
            <div className={styles.container}>
                <div className={styles.navigation}>
                    <Link to="/desk/selectAsset" className={styles.backLink}>{'<- 이전으로'}</Link>
                </div>
                <h1 className={styles.title}>응원의 말을 남겨주세요!</h1>

                <input
                    className={styles.nicknameInput}
                    placeholder="닉네임을 입력해주세요."
                    type="text"
                    value={nickname}
                    onChange={e => setNickname(e.target.value)}
                />

                <div className={styles.letterContainer}>

                    <textarea
                        className={styles.contentTextarea}
                        placeholder="응원의 글을 적어주세요."
                        value={content}
                        onChange={e => setContent(e.target.value)}
                    />

                    <div className={styles.radioGroup}>
                        <div className={styles.radioItem}>
                            <label>
                                <input
                                    type="radio"
                                    name="visibility"
                                    checked={isPublic}
                                    onChange={() => setIsPublic(true)}
                                />
                                공개
                            </label>
                        </div>
                        <div className={styles.radioItem}>
                            <label>
                                <input
                                    type="radio"
                                    name="visibility"
                                    checked={!isPublic}
                                    onChange={() => setIsPublic(false)}
                                />
                                비공개
                            </label>
                        </div>
                    </div>
                </div>
                <button className={styles.submitButton} onClick={handleSubmit}>응원 남기기</button>

                {showModal && (
                    <Modal closeModal={closeModal} handleModalConfirm={handleModalConfirm} />
                )}
            </div>
        </div >
    );
};

const Modal = ({ closeModal, handleModalConfirm }) => {
    return (
        <div className={styles.modalOverlay}>
            <div className={styles.modalContent}>
                <p>등록하면 수정할 수 없습니다.</p>
                <div className={styles.modalButtons}>
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
