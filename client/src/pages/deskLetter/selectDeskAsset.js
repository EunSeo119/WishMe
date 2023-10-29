import styleApp from '../../app.module.css'
import React, { useState, useEffect } from "react"; // useEffect import 추가
import { Link, useNavigate } from "react-router-dom";  // useNavigate import 추가
import style from "./selectDeskAsset.module.css";
import axios from 'axios';  // axios import

const SelectDeskAsset = () => {
    const [selected, setSelected] = useState(null);
    const [currentPage, setCurrentPage] = useState(1);
    const itemsPerPage = 9;
    const [assetInfo, setAssetInfo] = useState([]); // 상태 초기화 변경
    const [selectedAssetSeq, setSelectedAssetSeq] = useState(null); // 선택된 이미지의 assetSeq 값을 저장하는 상태

    useEffect(() => {
        // 백엔드 API 호출하여 이미지 URL 가져오기
        const AccessToken = localStorage.getItem("AccessToken");
        axios({
            method: "get",
            url: `http://localhost:8080/api/my/letter/assets`,
            headers: {
                Authorization: `Bearer ${AccessToken}`,
            },
        })

            .then(response => {
                const fetchedImageUrls = response.data.map(asset => asset);
                console.log(response.data)
                setAssetInfo(fetchedImageUrls);
            })
            .catch(error => {
                console.error("이미지를 가져오는데 실패했습니다.", error);
            });
    }, []);

    const navigate = useNavigate();  // useNavigate 초기화

    const handleNextButtonClick = () => {
        console.log("cc", selectedAssetSeq)
        if (selectedAssetSeq) {
            // 선택된 assetSeq 값을 사용하여 writeDeskLetter 페이지로 이동
            navigate(`/desk/writeLetter/${selectedAssetSeq}`);
        } else {
            alert("선물을 선택해주세요.");
        }
    }

    const handleImageClick = (index) => {
        setSelected(index + indexOfFirstItem);
        const selectedImage = assetInfo[index + indexOfFirstItem];
        console.log("selectedImage", selectedImage)
        setSelectedAssetSeq(selectedImage.assetSeq);
    }

    // 현재 페이지의 이미지만 표시하기 위한 로직
    const indexOfLastItem = currentPage * itemsPerPage;
    const indexOfFirstItem = indexOfLastItem - itemsPerPage;
    const currentImages = assetInfo.slice(indexOfFirstItem, indexOfLastItem);

    const handleClick = (pageNumber) => {
        setCurrentPage(pageNumber);
    };

    const renderImages = currentImages.map((imageSource, index) => (
        <div
            key={index}
            className={`${style.gridItem} ${selected === index + indexOfFirstItem ? style.selected : ""}`}
            onClick={() => handleImageClick(index)}
        >
            <img src={imageSource.assetImg} alt={`선물 ${index + 1 + indexOfFirstItem}`} />
        </div>
    ));

    const pageNumbers = [];
    for (let i = 1; i <= Math.ceil(assetInfo.length / itemsPerPage); i++) {
        pageNumbers.push(i);
    }

    const renderPageNumbers = pageNumbers.map(number => (
        <button key={number} onClick={() => handleClick(number)}>
            {number}
        </button>
    ));

    return (
        <div className={styleApp.app}>
            <div className={style.container}>
                <div className={style.navigation}>
                    <Link to="/desk" className={style.backLink}>{'<- 이전으로'}</Link>
                </div>
                <p className={style.title}>선물을 선택해주세요!</p>
                <div className={style.gridContainer}>
                    {renderImages}
                </div>
                <div className={style.pageNumbers}>
                    {renderPageNumbers}
                </div>
                <div className={style.buttonContainer}>
                    <button className={style.nextButton} onClick={handleNextButtonClick}>다음</button>
                </div>
            </div>
        </div>
    );
};

export default SelectDeskAsset;
