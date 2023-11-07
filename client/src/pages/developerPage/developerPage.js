import style from './developerPage.module.css'
import { Link } from 'react-router-dom'
import React, { useState, useEffect } from 'react'
import axios from 'axios'
import { Navigate, useNavigate } from 'react-router'
import ShareURLModal from '../../Modal/shareURLModal'
import { useParams } from 'react-router-dom'
import { IoIosArrowBack, IoIosArrowForward } from 'react-icons/io'
import Header from '../../Common/Header'

const DeveloperPage = () => {
  const { deskUuid } = useParams()
  const [page, setPage] = useState(1)
  const [isMine, setIsMine] = useState(false)
  const [deskName, setDeskName] = useState('test')
  const [totalCount, setTotalCount] = useState(0)
  const [deskLetter, setDeskLetter] = useState([])
  const [currentPage, setCurrentPage] = useState(1)
  const [totalPage, setTotalPage] = useState(1)
  const navigate = useNavigate()
  const SERVER_URL = process.env.REACT_APP_SERVER_URL

  // shareURLModal
  const [isModalOpen, setIsModalOpen] = useState(false)
  const openModal = () => {
    setIsModalOpen(true)
  }
  const closeModal = () => {
    setIsModalOpen(false)
  }

  const changePage = (newPage) => {
    if (newPage >= 1 && newPage <= totalPage) {
      setCurrentPage(newPage)
    }
  }

  const handleLetterClick = (letter) => {
    if (letter.public || letter.developer) {
      navigate(`/developerLetterDetail/${page}/${letter.myLetterSeq}`)
    } else {
      openNextDateModal()
    }
  }

  const [isNextDateModalOpen, setIsNextDateModalOpen] = useState(false)
  const openNextDateModal = () => {
    setIsNextDateModalOpen(true)
  }
  const closeNextDateModal = () => {
    setIsNextDateModalOpen(false)
  }

  // '내 책상 보기' 버튼 클릭 시 처리
  const handleMyDeskClick = () => {
    const AccessToken = localStorage.getItem('AccessToken')
    const RefreshToken = localStorage.getItem('RefreshToken')
    if (AccessToken) {
      // AccessToken이 있으면 내 책상 페이지로 이동
      axios({
        method: 'get',
        url: `${SERVER_URL}/api/my/letter/loginUserUuid`,
        headers: {
          Authorization: `Bearer ${AccessToken}`,
          RefreshToken: `${RefreshToken}`
        }
      })
        .then((response) => {
          // 여기 넣어줘
          const data = response.data
          navigate(`/desk/${data.loginUserUuid}`)
          // 여기 넣어줘
        })
        .catch((error) => {
          console.error('API 요청 중 오류 발생:', error)
        })
    } else {
      // AccessToken이 없으면 로그인 페이지로 이동
      navigate(`/`)
    }
  }

  useEffect(() => {
    const AccessToken = localStorage.getItem('AccessToken')
    const RefreshToken = localStorage.getItem('RefreshToken')
    const headers = {}

    if (AccessToken) {
      headers.Authorization = `Bearer ${AccessToken}`
      headers.RefreshToken = `${RefreshToken}`
    }

    axios({
      method: 'get',
      url: `${SERVER_URL}/api/developer/letter/all?page=${currentPage}`,
      headers
    })
      .then((response) => {
        const data = response.data
        setTotalCount(data.totalLetters)
        setDeskLetter(data.lettersPerPage)
        setTotalPage(data.totalPages)
      })
      .catch((error) => {
        console.error('API 요청 중 오류 발생:', error)
      })
  }, [currentPage])

  return (
    <div>
      <div className={style.main}>
        <img
          src="https://wishme-bichnali.s3.ap-northeast-2.amazonaws.com/background/deskBackground.png"
          className={style.bg}
          crossOrigin="anonymous"
        />

        {/* 헤더 */}
        <div className={style.header}>
          <Header />
        </div>

        {/* 제목 */}
        <div className={style.deskTitle}>
          <b>빛나리</b>님의 책상에
          <br />
          <b>{totalCount}</b>개의 응원이 왔어요!
        </div>

        {/* 편지 에셋 목록 */}
        <div className={style.deskLetterList}>
          <div
            className={`${style.arrowIcon} ${
              currentPage === 1 ? style.disabledArrow : style.abledArrow
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
            {deskLetter.map((letter, index) => (
              <div
                key={index}
                className={style.gridItem}
                onClick={() => handleLetterClick(letter)}
              >
                {/* <img src={`${letter.assetImg}`} /> */}
                <img src={`${letter.assetImg}`} crossOrigin="anonymous" />
                <p className={style.nickname}>{`${letter.fromUserNickname}`}</p>
              </div>
            ))}
          </div>

          <div
            className={`${style.arrowIcon} ${
              currentPage === totalPage ? style.disabledArrow : style.abledArrow
            }`}
            onClick={() => changePage(currentPage + 1)}
          >
            <IoIosArrowForward />
          </div>
        </div>

        <div className={style.btn}>
          {isMine ? (
            <>
              <div className={style.cheerUpBtn} onClick={openModal}>
                공유하기
              </div>
            </>
          ) : (
            <>
              <Link to={`/developer/selectAsset`} className={style.link}>
                <div className={style.cheerUpBtn}>응원 또는 문의하기</div>
              </Link>

              <div className={style.cheerUpBtn} onClick={handleMyDeskClick}>
                내 책상 보기
              </div>
            </>
          )}
        </div>

        {/* 편지 날짜 알림 모달*/}
        <ShareURLModal isOpen={isModalOpen} onClose={closeModal} />

        <div>
          {isNextDateModalOpen && (
            <div className={style.Modalmodal}>
              <div className={style.Modalclose} onClick={closeNextDateModal}>
                X
              </div>
              <div className={style.Modaltitle}>비공개 편지입니다.</div>
              <div className={style.Modalbtn} onClick={closeNextDateModal}>
                닫기
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
    // </div>
  )
}

export default DeveloperPage
