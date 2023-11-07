import React, { useState, useEffect } from 'react'
import axios from 'axios'
import styleSchool from './schoolPage.module.css' // CSS 모듈을 import
import 'slick-carousel/slick/slick.css'
import 'slick-carousel/slick/slick-theme.css'
import Slider from 'react-slick'
import { useParams } from 'react-router-dom'
import { useNavigate } from 'react-router-dom'
import { IoIosArrowBack, IoIosArrowForward, IoIosClose } from 'react-icons/io' // IoIosArrowForward를 import
import ShareURLModal from '../../Modal/shareURLModal'
import Header from '../../Common/Header'
import { BsToggle2Off, BsToggle2On } from 'react-icons/bs'
import MainPopup from '../../Modal/MainPopup'
import tokenHttp from '../../apis/tokenHttp'

const SchoolPage = () => {
  const { schoolUuid, letterPage } = useParams()
  const [page, setPage] = useState(letterPage ? Number(letterPage) : 1)
  const [schoolId, setSchoolId] = useState(1)
  const [schoolName, setSchoolName] = useState('')
  const [totalCount, setTotalCount] = useState(0)
  const [schoolLetter, setSchoolLetter] = useState([])
  const [totalPage, setTotalPage] = useState(1)
  const navigate = useNavigate()
  const SERVER_URL = process.env.REACT_APP_SERVER_URL;

  // 모달
  const [showMainPop, setShowMainPop] = useState(false)
  // 기본 세팅 값은 false
  const VISITED = localStorage.getItem('Visited')
  // localStorage에 homeVisited 조회

  useEffect(() => {
    if (!VISITED) {
      // 저장된 date가 없거나 today보다 작다면 popup 노출
      setShowMainPop(true)
    }
  }, [VISITED])

  const changePage = (newPage) => {
    console.log('페이지 변경')
    console.log(newPage, page, totalPage)
    if (newPage >= 1 && newPage <= totalPage) {
      setPage(newPage)
    }
  }

  const handleLetterClick = (letterId) => {
    navigate(`/schoolLetterDetail/${schoolUuid}/${letterId}/${page}`)
  }

  const letterWriteClick = (schoolUuid) => {
    navigate(`/schoolLetterAssetList/${schoolUuid}`)
  }

  // 학교/책상 토글
  const [isOn, setIsOn] = useState(false)

  const handleToggleClick = () => {
    setIsOn(!isOn)
    if (!isOn) {
      handleMyDeskClick()
    }
  }

  const [isModalOpen, setIsModalOpen] = useState(false)
  const openModal = () => {
    setIsModalOpen(true)
  }
  const closeModal = () => {
    setIsModalOpen(false)
  }

  const [isNextDateModalOpen, setIsNextDateModalOpen] = useState(false)
  const openNextDateModal = () => {
    setIsNextDateModalOpen(true)
  }
  const closeNextDateModal = () => {
    setIsNextDateModalOpen(false)
  }

  const handleMyDeskClick = () => {
    const AccessToken = localStorage.getItem('AccessToken')
    const RefreshToken = localStorage.getItem('RefreshToken')
    if (AccessToken) {
      // alert('내 책상으로 이동')
      // AccessToken이 있으면 내 책상 페이지로 이동
      tokenHttp({
        method: 'get',
        url: `${SERVER_URL}/api/my/letter/loginUserUuid`,
        headers: {
          Authorization: `Bearer ${AccessToken}`,
          RefreshToken: `${RefreshToken}`
        }
      })
        // .get(`http://localhost:8080/api/my/letter/all/${userUuid}?page=${page}`)
        .then((response) => {
          const data = response.data
          navigate(`/desk/${data.loginUserUuid}`)
        })
        .catch((error) => {
          // console.error('API 요청 중 오류 발생:', error)
          navigate(`/`)
        })
    } else {
      navigate(`/`)
    }
  }

  useEffect(() => {
    const pageNumber = letterPage ? Number(letterPage) : 1
    setPage(pageNumber)
    console.log('페이지변경[letterPage]' + page, pageNumber)
  }, [letterPage])

  useEffect(() => {
    // console.log('페이지변경' + page)
    console.log('페이지변경[pate]' + page)

    axios
      .get(
        `${SERVER_URL}/api/school/letter/allByUUID/${schoolUuid}/${page}`
      )
      // .get(
      //   `http://localhost:8082/api/school/letter/allByUUID/${schoolUuid}/${page}`
      // )
      .then((response) => {
        const data = response.data
        // console.log(data)
        setSchoolName(data.schoolName)
        setTotalCount(data.totalCount)
        setSchoolLetter(data.schoolLetterList)
        setTotalPage(data.totalPage)
        setSchoolId(data.schoolId)
        localStorage.setItem('schoolUuid', schoolUuid)
        setTotalPage(data.totalPage)
        console.log(page, totalPage)
      })
      .catch((error) => {
        // console.error('API 요청 중 오류 발생:', error)
      })
  }, [page])

  return (
    <div>
      <div className={styleSchool.main}>
        <img
          crossOrigin="anonymous"
          src="https://wishme-bichnali.s3.ap-northeast-2.amazonaws.com/background/schoolBackground.png"
          className={styleSchool.bg}
        />

        {/* 헤더 */}
        <div className={styleSchool.header}>
          <Header />
        </div>
        {/* 모달 */}
        {showMainPop && <MainPopup setShowMainPop={setShowMainPop}></MainPopup>}
        {/* 학교/내책상 토글 */}
        <div className={styleSchool.toggle}>
          <div>
            <b>책상</b>
          </div>
          {/* <div>{isOn ? <BsToggleOn onClick={handleToggleClick} /> : <BsToggleOff onClick={handleToggleClick} />}</div> */}
          <div>
            {isOn ? (
              <BsToggle2Off onClick={handleToggleClick} />
            ) : (
              <BsToggle2On onClick={handleToggleClick} />
            )}
          </div>
          <div>
            <b>학교</b>
          </div>
        </div>

        {/* 급훈문구 */}
        <div className={styleSchool.schoolName}>
          <b>{schoolName}</b>에
          <b>
            <br></br>
            {totalCount}
          </b>
          개의 응원이 왔어요!
        </div>

        {/* 편지 에셋 목록 */}
        <div className={styleSchool.gridContainer}>
          <div
            className={`${styleSchool.arrowIcon} ${page === 1 ? styleSchool.disabledArrow : ''
              }`}
            onClick={() => {
              if (page > 1) {
                changePage(page - 1)
              }
            }}
          >
            <IoIosArrowBack />
          </div>
          <div className={styleSchool.gridItemContainer}>
            {schoolLetter.slice(0, 12).map((letter, index) => (
              <div
                key={index}
                className={styleSchool.gridItem}
                onClick={() => handleLetterClick(letter.schoolLetterSeq)}
              >
                <img crossorigin="anonymous" src={`${letter.assetImg}`} />
              </div>
            ))}
          </div>
          <div
            className={`${styleSchool.arrowIcon} ${page === totalPage ? styleSchool.disabledArrow : ''
              }`}
            onClick={() => changePage(page + 1)}
          >
            <IoIosArrowForward />
          </div>
        </div>
        {/* 여기가 끝 */}
        <ShareURLModal isOpen={isModalOpen} onClose={closeModal} />
        {/* 여기가 모달*/}

        <div>
          {isNextDateModalOpen && (
            <div className={styleSchool.Modalmodal}>
              {/* <div className={style.header}> */}
              <div
                className={styleSchool.Modalclose}
                onClick={closeNextDateModal}
              >
                <IoIosClose />
              </div>
              <div className={styleSchool.Modaltitle}>
                편지는 11월 11일<br></br> 공개됩니다!
              </div>
              <div
                className={styleSchool.Modalbtn}
                onClick={closeNextDateModal}
              >
                닫기
              </div>
            </div>
          )}
        </div>
      </div>
      {/* 편지 에셋 목록 */}
      <div className={styleSchool.btn}>
        {/* <div style={{display:'flex', justifyContent:'space-around'}}> */}
          <div className={styleSchool.mydeskBtn} onClick={() => handleMyDeskClick()}>
            나도 응원 받기
          </div>
          <div className={styleSchool.rowButton}>
            <div className={styleSchool.mySchoolBtn} onClick={() => letterWriteClick(schoolUuid)}>
              응원하기
            </div>
            <div className={styleSchool.mySchoolBtnHalfShare} onClick={openModal}>
              학교 공유하기
            </div>
          </div>
        {/* </div> */}
      </div>
    </div>
  )
}

export default SchoolPage
