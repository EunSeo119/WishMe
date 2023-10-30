import style from "./deskPage.module.css";
import styleApp from "../../app.module.css";
import { Link } from "react-router-dom";
import React, { useState, useEffect } from "react";
import axios from "axios";
import { Navigate, useNavigate } from "react-router";
import ShareURLModal from "../../Modal/shareURLModal";

const DeskPage = () => {
  const [page, setPage] = useState(1);
  const [deskUuid, setDeskUuid] = useState(1);
  const [isMine, setIsMine] = useState(true);
  const [deskName, setDeskName] = useState("test");
  const [totalCount, setTotalCount] = useState(0);
  const [deskLetter, setDeskLetter] = useState([]);
  // const [totalPage, setTotalPage] = useState(1);
  const navigate = useNavigate();

  useEffect(() => {
    setDeskUuid(localStorage.getItem('deskUuid')); // 원하는 값으로 변경
  }, []);

  // shareURLModal
  const [isModalOpen, setIsModalOpen] = useState(false);
  const openModal = () => {
    setIsModalOpen(true);
  };
  const closeModal = () => {
    setIsModalOpen(false);
  };

  // var url = 'http://localhost:8082/'

  useEffect(() => {
    axios
      .get(`http://localhost:8081/api/my/letter/all/${deskUuid}?page=${page}`)
      .then((response) => {
        const data = response.data;
        console.log(data);
        setDeskName(data.toUserNickname);
        setTotalCount(data.totalLetterCount);
        setDeskLetter(data.myLetterResponseDtoList);
        setIsMine(data.isMine);
        // setTotalPage(data.totalPage)
      })
      .catch((error) => {
        console.error("API 요청 중 오류 발생:", error);
      });
  }, [deskUuid, page]);

  return (
    // <div className={styleApp.app}>
    <div className={style.deskPage}>
      <img
        src="https://wishme-bichnali.s3.ap-northeast-2.amazonaws.com/background/deskBackground.PNG"
        className={styleApp.bg}
      />
      <div className={style.board}>
        <div className={style.title}>
          <b>{deskName}</b>님의 책상에
          <br />
          <b>{totalCount}</b>의 응원이 왔어요!
        </div>
      </div>
      <div className={style.desk}>
        <div className={style.gridContainer}>
          {deskLetter.slice(0, 9).map((letter, index) => (
            <div key={index} className={style.gridItem}>
              {/* <img src={`${letter.assetImg}`} /> */}
              <img src={`${letter}`} />
            </div>
          ))}
        </div>
      </div>
      <div className={style.btn}>
        {isMine ? (
          <>
            <div className={style.shareBtn} onClick={openModal}>
              공유하기
            </div>
            <ShareURLModal isOpen={isModalOpen} onClose={closeModal} />
          </>
        ) : (
          <>
            <Link to="/desk/selectAsset" className={style.link}>
              <div className={style.cheerUpBtn} onClick={() => navigate("/")}>
                응원하기
              </div>
            </Link>
            <div className={style.myDeskBtn}>내 책상 보기</div>
          </>
        )}
      </div>
    </div>
    // </div>
  );
};

export default DeskPage;
