import style from './deskPage.module.css'
import { Link } from 'react-router-dom'
import React, { useState, useEffect } from 'react'
import { useNavigate } from 'react-router'
import ShareURLModal from '../../Modal/shareURLModal'
import { useParams } from 'react-router-dom'
import { IoIosArrowBack, IoIosArrowForward, IoIosClose } from 'react-icons/io'
import { BsToggle2Off, BsToggle2On } from 'react-icons/bs'
import Header from '../../Common/Header'
import tokenHttp from '../../apis/tokenHttp'

const DeskPage = () => {
  const { deskUuid, letterPage } = useParams()
  const [isMine, setIsMine] = useState(false)
  const [deskName, setDeskName] = useState('test')
  const [totalCount, setTotalCount] = useState(0)
  const [deskLetter, setDeskLetter] = useState([])
  const [currentPage, setCurrentPage] = useState(letterPage ? Number(letterPage) : 1)
  const [totalPage, setTotalPage] = useState(1)
  const navigate = useNavigate()
  const USER_SERVER = process.env.REACT_APP_USER_SERVER;
  const MYLETTER_SERVER = process.env.REACT_APP_MYLETTER_SERVER;

  
  // 학교/책상 토글
  const [isOn, setIsOn] = useState(false)

  const handleToggleClick = () => {
    setIsOn(!isOn)
    if (!isOn) {
      handleMySchoolClick()
    }
  }

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
    const currentDate = new Date()
    const modalOpenDate = new Date('2023-11-11')

    if (currentDate < modalOpenDate) {
      // 현재 날짜가 2023년 11월 11일 이전이면 모달 열기
      openNextDateModal()
    } else if (letter.isPublic || isMine) {
      navigate(`/deskLetterDetail/${deskUuid}/${letter.myLetterSeq}/${currentPage}`)
    } else {
      openPrivateLetterModal()
    }
  }

  const [isPrivateLetterModal, setIsPrivateLetterModal] = useState(false)
  const openPrivateLetterModal = () => {
    setIsPrivateLetterModal(true)
  }
  const closePrivateLetterModal = () => {
    setIsPrivateLetterModal(false)
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
    const RefreshToken = localStorage.getItem("RefreshToken");
    if (AccessToken) {
      // AccessToken이 있으면 내 책상 페이지로 이동
      tokenHttp({
        method: 'get',
        url: `${MYLETTER_SERVER}/api/my/letter/loginUserUuid`,
        headers: {
          Authorization: `Bearer ${AccessToken}`,
          RefreshToken: `${RefreshToken}`
        }
      })
        .then((response) => {
          const data = response.data
          navigate(`/desk/${data.loginUserUuid}`)
        })
        .catch((error) => {
          console.error('API 요청 중 오류 발생:', error)
        })
    } else {
      // AccessToken이 없으면 로그인 페이지로 이동
      navigate(`/`)
    }
  }

  // '우리 학교 가기' 버튼 클릭 시 처리
  const handleMySchoolClick = () => {
    const AccessToken = localStorage.getItem('AccessToken')
    const RefreshToken = localStorage.getItem("RefreshToken");
    tokenHttp({
      method: 'get',
      url: `${USER_SERVER}/api/users`,
      headers: {
        Authorization: `Bearer ${AccessToken}`,
        RefreshToken: `${RefreshToken}`
      }
    })
      .then((response) => {
        const data = response.data

        if (data.data.schoolUuid) {
          navigate(`/school/${data.data.schoolUuid}`)
        } else {
          // 학교 저장을 한적이 없으면 학교 검색 페이지로 이동
          navigate(`/searchSchool`)
        }
      })
      .catch((error) => {
        console.error('API 요청 중 오류 발생:', error)
      })
  }

  useEffect(() => {
    const pageNumber = letterPage ? Number(letterPage) : 1
    setCurrentPage(pageNumber)
  }, [letterPage])

  useEffect(() => {
    const AccessToken = localStorage.getItem('AccessToken')
    const RefreshToken = localStorage.getItem("RefreshToken");
    const headers = {}

    if (AccessToken) {
      headers.Authorization = `Bearer ${AccessToken}`;
      headers.RefreshToken = `${RefreshToken}`;
    }
    tokenHttp({
      method: 'get',
      url: `${MYLETTER_SERVER}/api/my/letter/all/${deskUuid}?page=${currentPage}`,
      headers
    })
      .then((response) => {
        const data = response.data
        setDeskName(data.toUserNickname)
        setTotalCount(data.totalLetterCount)
        setDeskLetter(data.myLetterResponseDtoList)
        setIsMine(data.mine)
        setTotalPage(Math.ceil(data.totalLetterCount / 9))
      })
      .catch((error) => {
        console.error('API 요청 중 오류 발생:', error)
      })
  }, [currentPage, deskUuid])

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

        {/* 학교/내책상 토글 */}
        {isMine ? (
          <div className={style.toggle}>
            <div>
              <b>책상</b>
            </div>
            <div>
              {isOn ? (
                <BsToggle2On onClick={handleToggleClick} />
              ) : (
                <BsToggle2Off onClick={handleToggleClick} />
              )}
            </div>
            <div>
              <b>학교</b>
            </div>
          </div>
        ) : (
          <></>
        )}

        {/* 제목 */}
        <div className={style.deskTitle}>
          <b>{deskName}</b>님의 책상에
          <br />
          <b>{totalCount}</b>개의 응원이 왔어요!
        </div>

        {/* 편지 에셋 목록 */}
        <div className={style.deskLetterList}>
          <div
            className={`${style.arrowIcon} ${currentPage === 1 ? style.disabledArrow : style.abledArrow
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
              <div
                key={index}
                className={style.gridItem}
                onClick={() => handleLetterClick(letter)}
              >
                <img src={`${letter.assetImg}`} crossOrigin="anonymous" />
                <p className={style.nickname}>{`${letter.fromUserNickname}`}</p>
              </div>
            ))}
          </div>
          <div
            className={`${style.arrowIcon} ${currentPage === totalPage ? style.disabledArrow : style.abledArrow
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
                내 책상 공유하기
              </div>
            </>
          ) : (
            <>
              {localStorage.getItem('AccessToken') ? (
                <>
                  <Link
                    to={`/desk/${deskUuid}/selectAsset`}
                    className={style.link}
                  >
                    <div className={style.cheerUpBtn}>응원하기</div>
                  </Link>
                </>
              ) : (
                <>
                  <Link
                    to={`/desk/${deskUuid}/checkLogin`}
                    className={style.link}
                  >
                    <div className={style.cheerUpBtn2}>응원하기</div>
                  </Link>
                </>
              )}
              <div className={style.cheerUpBtn3} onClick={handleMyDeskClick}>
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
                <IoIosClose />
              </div>
              <div className={style.Modaltitle}>
                편지는 11월 11일<br></br> 공개됩니다!
              </div>
              <div className={style.Modalbtn} onClick={closeNextDateModal}>
                닫기
              </div>
            </div>
          )}
        </div>

        {/* 비공개 편지 알림 모달*/}
        <div>
          {isPrivateLetterModal && (
            <div className={style.Modalmodal}>
              <div className={style.Modalclose} onClick={closePrivateLetterModal}>
                X
              </div>
              <div className={style.Modaltitle}>비공개 편지입니다.</div>
              <div className={style.Modalbtn} onClick={closePrivateLetterModal}>
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

export default DeskPage
