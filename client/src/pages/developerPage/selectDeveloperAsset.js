import React, { useState, useEffect } from "react"; // useEffect import 추가
import { Link, useNavigate } from "react-router-dom";  // useNavigate import 추가
import style from "./selectDeveloperAsset.module.css";
import axios from 'axios';  // axios import
import { IoIosArrowBack, IoIosArrowForward } from 'react-icons/io'
import tokenHttp from "../../apis/tokenHttp";

const SelectDeveloperAsset = () => {
    const [selected, setSelected] = useState(null);
    const [currentPage, setCurrentPage] = useState(1);
    const itemsPerPage = 9;
    const [assetInfo, setAssetInfo] = useState([]); // 상태 초기화 변경
    const [selectedAssetSeq, setSelectedAssetSeq] = useState(null); // 선택된 이미지의 assetSeq 값을 저장하는 상태
    const [totalPage, setTotalPage] = useState(1)
    const SERVER_URL = process.env.REACT_APP_SERVER_URL;

    const changePage = (newPage) => {
        console.log(totalPage)
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
            url: `${SERVER_URL}/api/my/letter/assets`,
            headers
        })

            .then(response => {
                const fetchedImageUrls = response.data.map(asset => asset);
                setAssetInfo(fetchedImageUrls);
                setTotalPage(Math.ceil(fetchedImageUrls.length / itemsPerPage)); // totalPage 상태 업데이트
            })
            .catch(error => {
                console.error("이미지를 가져오는데 실패했습니다.", error);
            });
    }, []);

    const navigate = useNavigate();  // useNavigate 초기화

    const handleNextButtonClick = () => {
        if (selectedAssetSeq) {
            // 선택된 assetSeq 값을 사용하여 writeDeskLetter 페이지로 이동
            navigate(`/developer/writeLetter/${selectedAssetSeq}`);
        } else {
            alert("선물을 선택해주세요.");
        }
    }

    const handleImageClick = (index) => {
        setSelected(index + indexOfFirstItem);
        const selectedImage = assetInfo[index + indexOfFirstItem];
        setSelectedAssetSeq(selectedImage.assetSeq);
    }

    // 현재 페이지의 이미지만 표시하기 위한 로직
    const indexOfLastItem = currentPage * itemsPerPage;
    const indexOfFirstItem = indexOfLastItem - itemsPerPage;
    const currentImages = assetInfo.slice(indexOfFirstItem, indexOfLastItem);

    const renderImages = currentImages.map((imageSource, index) => (
        <div
            key={index}
            className={`${style.gridItem} ${selected === index + indexOfFirstItem ? style.selected : ""}`}
            onClick={() => handleImageClick(index)}
        >
            <img src={imageSource.assetImg} alt={`선물 ${index + 1 + indexOfFirstItem}`} crossOrigin="anonymous" />
        </div>
    ));

    const pageNumbers = [];
    for (let i = 1; i <= Math.ceil(assetInfo.length / itemsPerPage); i++) {
        pageNumbers.push(i);
    }

    return (
        <div className={style.body}>
            <div className={style.navigation}>
                <IoIosArrowBack className={style.icon} />
                <Link to={`/developer`} className={style.backLink}>이전으로</Link>
            </div>
            <p className={style.title}>선물을 선택해주세요!</p>
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
                    className={style.mySchoolBtn}
                    onClick={() => handleNextButtonClick()}
                >
                    다음
                </div>
            </div>
        </div>
    );
};

export default SelectDeveloperAsset;
