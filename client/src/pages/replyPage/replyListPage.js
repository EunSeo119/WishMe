import React, { useState, useEffect } from "react"; // useEffect import 추가
import { Link, useNavigate } from "react-router-dom";  // useNavigate import 추가
import style from "./replyListPage.module.css";
import { IoIosArrowBack, IoIosArrowForward } from 'react-icons/io'
import tokenHttp from "../../apis/tokenHttp";

const ReplyListPage = () => {
    const [selected, setSelected] = useState(null);
    const [currentPage, setCurrentPage] = useState(1);
    const itemsPerPage = 9;
    const [totalPage, setTotalPage] = useState(1)
    const [ReplyListName, setReplyListName] = useState('test')
    const [totalCount, setTotalCount] = useState(0)
    const [replyLetterList, setReplyLetterList] = useState([])
    const MYLETTER_SERVER = process.env.REACT_APP_MYLETTER_SERVER;

    const changePage = (newPage) => {
        if (newPage >= 1 && newPage <= totalPage) {
            setCurrentPage(newPage)
        }
    }

    useEffect(() => {
        // 백엔드 API 호출하여 이미지 URL 가져오기
        const AccessToken = localStorage.getItem("AccessToken");
        const RefreshToken = localStorage.getItem("RefreshToken");
        const headers = {};

        if (AccessToken) {
            headers.Authorization = `Bearer ${AccessToken}`;
            headers.RefreshToken = `${RefreshToken}`;
        }
        tokenHttp({
            method: "get",
            url: `${MYLETTER_SERVER}/api/my/reply/all?page=${currentPage}`,
            headers
        })

            .then(response => {
                const data = response.data
                setReplyListName(data.toUserNickname)
                setTotalCount(data.totalReplyCount)
                setReplyLetterList(data.myReplyResponseDtos)
                setTotalPage(Math.ceil(data.totalReplyCount / 9))
            })
            .catch(error => {
                console.error("이미지를 가져오는데 실패했습니다.", error);
            });
    }, []);

    const navigate = useNavigate();  // useNavigate 초기화

    //이전으로
    const goPre = () => {
        navigate(-1)
    }

    const handleImageClick = (index) => {
        setSelected(index + indexOfFirstItem);
        const selectedReply = replyLetterList[index + indexOfFirstItem];
        navigate(`/replyDetailPage/${selectedReply.replySeq}`);
    }

    // 현재 페이지의 이미지만 표시하기 위한 로직
    const indexOfLastItem = currentPage * itemsPerPage;
    const indexOfFirstItem = indexOfLastItem - itemsPerPage;
    const currentReplies = replyLetterList.slice(indexOfFirstItem, indexOfLastItem);

    const renderImages = currentReplies.map((replySource, index) => {
        let imageUrl;

        switch (replySource.color) {
            case 'B':
                imageUrl = 'https://wishme-bichnali.s3.ap-northeast-2.amazonaws.com/letter/blueButton.png';
                break;
            case 'P':
                imageUrl = 'https://wishme-bichnali.s3.ap-northeast-2.amazonaws.com/letter/pinkButton.png';
                break;
            case 'Y':
                imageUrl = 'https://wishme-bichnali.s3.ap-northeast-2.amazonaws.com/letter/yellowButton.png';
                break;
            default:
                imageUrl = replySource.color; // 다른 색상에 대한 기본 URL
        }

        return (
            <div
                key={index}
                className={`${style.gridItem} ${selected === index + indexOfFirstItem ? style.selected : ""}`}
                onClick={() => handleImageClick(index)}
            >
                <div style={{ position: 'relative' }}>
                    <img src={imageUrl} alt={`선물 ${index + 1 + indexOfFirstItem}`} crossOrigin="anonymous" />
                    <div className={style.nickname}>
                        {replySource.fromUserNickname} {/* 닉네임 출력 */}
                    </div>
                </div>
            </div>
        );
    });


    const pageNumbers = [];
    for (let i = 1; i <= Math.ceil(replyLetterList.length / itemsPerPage); i++) {
        pageNumbers.push(i);
    }

    return (
        <div className={style.body}>
            <div className={style.navigation} onClick={() => goPre()}>
                <IoIosArrowBack className={style.icon} />
                이전으로
            </div>
            {/* 제목 */}
            <div className={style.deskTitle}>
                <b>{ReplyListName}</b> 님의 책상에
                <br />
                <b>{totalCount}</b>개의 응원이 왔어요!
            </div>
            <div className={style.gridContainer}>
                <div
                    className={`${style.arrowIcon} ${currentPage === 1 ? style.disabledArrow : ''
                        }`}
                    onClick={() => {
                        if (currentPage > 1) {
                            changePage(currentPage - 1)
                        }
                    }}
                >
                    <IoIosArrowBack />
                </div>
                <div className={style.gridItemContainer}>
                    {renderImages}
                </div>
                <div
                    className={`${style.arrowIcon} ${currentPage === totalPage ? style.disabledArrow : ''
                        }`}
                    onClick={() => changePage(currentPage + 1)}
                >
                    <IoIosArrowForward />
                </div>
            </div>
            <div className={style.btn}>
                <div
                    className={style.closeBtn}
                    onClick={() => goPre()}
                >
                    닫기
                </div>
            </div>
        </div>
    );
};

export default ReplyListPage;
