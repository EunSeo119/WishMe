import React from "react";
import style from "./letterReportModal.module.css";
import { useEffect } from "react";
import { IoIosArrowBack, IoIosArrowForward, IoIosClose } from "react-icons/io"; // IoIosArrowForward를 import
import { useNavigate } from "react-router-dom";
import axios from 'axios'

function LetterReportModal({ isOpen, onClose, isSchool, letterId }) {
    const MYLETTER_SERVER = process.env.REACT_APP_MYLETTER_SERVER
    const SCHOOL_SERVER = process.env.REACT_APP_SCHOOL_SERVER

    useEffect(() => { }, []);
    const navigate = useNavigate();

    // 페이지 URL 공유하기
    const letterReport = async () => {
        try {
            if (isSchool) {
                // await 학교 편지 신고 로직작성
                axios({
                    method: "put",
                    url: `${SCHOOL_SERVER}/api/school/letter/report/${letterId}`,
                })
                    .then((response) => {
                        alert("학교편지 신고가 완료되었습니다.");
                        navigate(-1);
                    })
                    .catch((error) => {
                        alert("학교편지 신고에 실패하였습니다.");
                    });
            } else {
                const AccessToken = localStorage.getItem('AccessToken')
                const RefreshToken = localStorage.getItem('RefreshToken')

                if (AccessToken) {
                    // await 개인 편지 신고 로직작성
                    axios({
                        method: "put",
                        url: `${MYLETTER_SERVER}/api/my/letter/report/${letterId}`,
                        headers: {
                            Authorization: `Bearer ${AccessToken}`,
                            RefreshToken: `${RefreshToken}`
                        }
                    })
                        .then((response) => {
                            alert("개인편지 신고가 완료되었습니다.");
                            navigate(-1);
                        })
                        .catch((error) => {
                            alert("개인편지 신고에 실패하였습니다.");
                        });
                } else {
                    alert("신고할 권한이 없습니다.");
                }

            }
        } catch (err) {
        }
    };

    if (!isOpen) return null;

    return (
        <div className={style.Modalmodal}>
            <div className={style.Modalclose} onClick={onClose}>
                <IoIosClose />
            </div>
            <div className={style.Modaltitle}>편지를 신고하시겠습니까?</div>
            <div className={style.ModalContent}>
                신고 후에는 관리자가 확인 후 공개 여부를 결정합니다.
            </div>
            <div className={style.rowButton}>
                <div className={style.ModalbtnCancle} onClick={onClose}>
                    취소
                </div>
                <div
                    className={style.ModalbtnReport}
                    onClick={() => letterReport()}
                >
                    신고하기
                </div>
            </div>
        </div>
    );
}

export default LetterReportModal;
