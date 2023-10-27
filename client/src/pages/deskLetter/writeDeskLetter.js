import styleApp from '../../app.module.css'
import React, { useState, useEffect } from "react"; // useEffect import 추가
import { Link } from "react-router-dom";
import style from "./writeDeskLetter.module.css";
import axios from 'axios';  // axios import

const WriteDeskLetter = () => {
    const [selected, setSelected] = useState(null);
    const [currentPage, setCurrentPage] = useState(1);
    const itemsPerPage = 9;
    const [imageSources, setImageSources] = useState([]); // 상태 초기화 변경

    useEffect(() => {
        // 백엔드 API 호출하여 이미지 URL 가져오기
        axios.get('http://localhost:8080/api/my/letter/assets')
            .then(response => {
                const fetchedImageUrls = response.data.map(asset => asset.assetImg);
                console.log(response.data)
                setImageSources(fetchedImageUrls);
            })
            .catch(error => {
                console.error("이미지를 가져오는데 실패했습니다.", error);
            });
    }, []);

    // 현재 페이지의 이미지만 표시하기 위한 로직
    const indexOfLastItem = currentPage * itemsPerPage;
    const indexOfFirstItem = indexOfLastItem - itemsPerPage;
    const currentImages = imageSources.slice(indexOfFirstItem, indexOfLastItem);

    const handleClick = (pageNumber) => {
        setCurrentPage(pageNumber);
    };

    const renderImages = currentImages.map((imageSource, index) => (
        <div
            key={index}
            className={`${style.gridItem} ${selected === index + indexOfFirstItem ? style.selected : ""}`}
            onClick={() => setSelected(index + indexOfFirstItem)}
        >
            <img src={imageSource} alt={`선물 ${index + 1 + indexOfFirstItem}`} />
        </div>
    ));

    const pageNumbers = [];
    for (let i = 1; i <= Math.ceil(imageSources.length / itemsPerPage); i++) {
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
                    <button className={style.nextButton}>다음</button>
                </div>
            </div>
        </div>
    );
};

export default WriteDeskLetter;
