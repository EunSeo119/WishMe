import style from "./deskPage.module.css";
// import styleApp from "../../app.module.css";
import { Link } from "react-router-dom";
import React, { useState, useEffect } from "react";
import axios from "axios";
import { Navigate, useNavigate } from "react-router";
import ShareURLModal from "../../Modal/shareURLModal";
import { useParams } from 'react-router-dom';
import { IoIosArrowBack, IoIosArrowForward } from 'react-icons/io'


const DeskPage = () => {
  const { deskUuid } = useParams();
  const [page, setPage] = useState(1);
  // const [deskUuid, setDeskUuid] = useState("");
  const [isMine, setIsMine] = useState(false);
  const [deskName, setDeskName] = useState("test");
  const [totalCount, setTotalCount] = useState(0);
  const [deskLetter, setDeskLetter] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPage, setTotalPage] = useState(1)
  // const [totalPage, setTotalPage] = useState(1)
  const navigate = useNavigate();
  const SERVER_URL = process.env.REACT_APP_SERVER_URL;

  // shareURLModal
  const [isModalOpen, setIsModalOpen] = useState(false);
  const openModal = () => {
    setIsModalOpen(true);
  };
  const closeModal = () => {
    setIsModalOpen(false);
  };

  const changePage = (newPage) => {
    if (newPage >= 1 && newPage <= totalPage) {
      setCurrentPage(newPage);
    }
  };

  // '내 책상 보기' 버튼 클릭 시 처리
  const handleMyDeskClick = () => {
    const AccessToken = localStorage.getItem("AccessToken");
    if (AccessToken) {
      // AccessToken이 있으면 내 책상 페이지로 이동
      axios({
        method: "get",
        url: `${SERVER_URL}/api/my/letter/loginUserUuid`,
        headers: {
          Authorization: `Bearer ${AccessToken}`,
        },
      })
        // .get(`http://localhost:8080/api/my/letter/all/${userUuid}?page=${page}`)
        .then((response) => {
          const data = response.data;
          navigate(`/desk/${data.loginUserUuid}`);
        })
        .catch((error) => {
          console.error("API 요청 중 오류 발생:", error);
        });
    } else {
      // AccessToken이 없으면 로그인 페이지로 이동
      navigate(`/`);
    }
  };


  // var url = 'http://localhost:8082/'

  useEffect(() => {
    const AccessToken = localStorage.getItem("AccessToken");
    const headers = {};

    if (AccessToken) {
      headers.Authorization = `Bearer ${AccessToken}`;
    }
    // const DeskUuid = localStorage.getItem("deskUuid");
    axios({
      method: "get",
      url: `${SERVER_URL}/api/my/letter/all/${deskUuid}?page=${currentPage}`,
      headers,
    })
      // .get(`http://localhost:8080/api/my/letter/all/${userUuid}?page=${page}`)
      .then((response) => {
        const data = response.data;
        console.log(data.myLetterResponseDtoList.length);
        setDeskName(data.toUserNickname);
        setTotalCount(data.totalLetterCount);
        setDeskLetter(data.myLetterResponseDtoList);
        setIsMine(data.mine);
        setTotalPage(Math.ceil(data.totalLetterCount / 9));
        // setTotalPage(data.totalPage)
      })
      .catch((error) => {
        console.log(SERVER_URL)
        console.error("API 요청 중 오류 발생:", error);
      });
  }, [currentPage, deskUuid]);

  return (
    <div className={style.app}>
      <div className={style.deskPage}>
        <img
          src="https://wishme-bichnali.s3.ap-northeast-2.amazonaws.com/background/deskBackground.png"
          className={style.bg}
          crossOrigin="anonymous"
        />
        <div className={style.board}>
          <div className={style.title}>
            <b>{deskName}</b>님의 책상에
            <br />
            <b>{totalCount}</b>개의 응원이 왔어요!
          </div>
        </div>
        <div className={style.desk}>

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

          <div className={style.gridContainer}>
            {deskLetter.slice(0, 9).map((letter, index) => (
              <div key={index} className={style.gridItem}>
                {/* <img src={`${letter.assetImg}`} /> */}
                <img src={`${letter.assetImg}`} crossOrigin="anonymous" />
                <p className={style.nickname}>{`${letter.fromUserNickname}`}</p>
              </div>
            ))}
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
          {isMine ? (
            <>
              <div className={style.shareBtn} onClick={openModal}>
                공유하기
              </div>
              <ShareURLModal isOpen={isModalOpen} onClose={closeModal} />
            </>
          ) : (
            <>
            {
              localStorage.getItem("AccessToken") ? (
                <>
                  <Link to={`/desk/${deskUuid}/selectAsset`} className={style.link}>
                    <div className={style.cheerUpBtn}>
                      응원하기
                    </div>
                  </Link>
                </>
              ) : (
                <>
                  <Link to={`/desk/${deskUuid}/checkLogin`} className={style.link}>
                    <div className={style.cheerUpBtn}>
                      응원하기
                    </div>
                  </Link>
                </>
              )
            }
              
              <div className={style.cheerUpBtn} onClick={handleMyDeskClick}>
                내 책상 보기
              </div>
            </>
          )}
        </div>
      </div>
    </div>
    // </div>
  );
};

export default DeskPage;
