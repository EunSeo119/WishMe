import React, { useState, useEffect } from 'react'
import axios from 'axios'
import styleSchool from './schoolPage.module.css' // CSS 모듈을 import
import 'slick-carousel/slick/slick.css'
import 'slick-carousel/slick/slick-theme.css'
import Slider from 'react-slick'
import { useParams } from 'react-router-dom'
import { useNavigate } from 'react-router-dom'
import { IoIosArrowBack, IoIosArrowForward } from 'react-icons/io' // IoIosArrowForward를 import
import ShareURLModal from '../../Modal/shareURLModal'

const SchoolPage = () => {
  const [page, setPage] = useState(1)
  const [schoolId, setSchoolId] = useState(1)
  // const [schoolUuid, setSchoolUuid] = useState(
  //   '9d7b90e2-e52c-4e90-ba5e-d661893b226d'
  // )
  const [schoolName, setSchoolName] = useState('')
  const [totalCount, setTotalCount] = useState(0)
  const [schoolLetter, setSchoolLetter] = useState([])
  const [totalPage, setTotalPage] = useState(1)
  const navigate = useNavigate()
  const { schoolUuid } = useParams()

  //   const settings = {
  //     dots: true,
  //     infinite: false,
  //     speed: 500,
  //     slidesToShow: 1,
  //     slidesToScroll: 1,
  //     afterChange: (current) => {
  //       changePage(current + 1)
  //     }
  //   }

  const changePage = (newPage) => {
    if (newPage >= 1 && newPage <= totalPage) {
      setPage(newPage)
    }
  }

  const handleLetterClick = (letterId) => {
    const currentDate = new Date()
    const modalOpenDate = new Date('2023-11-11')

    if (currentDate < modalOpenDate) {
      // 현재 날짜가 2023년 11월 11일 이전이면 모달 열기
      openNextDateModal()
    } else {
      // 그 이후면 페이지로 이동
      navigate(`/schoolLetterDetail/${letterId}`)
    }
  }

  const letterWriteClick = (schoolUuid) => {
    navigate(`/schoolLetterAssetList/${schoolUuid}`)
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
    if (AccessToken) {
      // alert('내 책상으로 이동')
      console.log(AccessToken)
      // AccessToken이 있으면 내 책상 페이지로 이동
      axios({
        method: 'get',
        url: `https://wishme.co.kr/api/my/letter/loginUserUuid`,
        headers: {
          Authorization: `Bearer ${AccessToken}`
        }
      })
        // .get(`http://localhost:8080/api/my/letter/all/${userUuid}?page=${page}`)
        .then((response) => {
          const data = response.data
          navigate(`/desk/${data.loginUserUuid}`)
        })
        .catch((error) => {
          console.error('API 요청 중 오류 발생:', error)
          //여기서도 로그인페이지로 이동
          navigate(`/`)
        })
    } else {
      // AccessToken이 없으면 로그인 페이지로 이동
      alert('로그인페이지로 이동')
      navigate(`/`)
    }
  }

  useEffect(() => {
    axios
      .get(
        `https://wishme.co.kr/api/school/letter/allByUUID/${schoolUuid}/${page}`
      )
      // .get(
      //   `http://localhost:8082/api/school/letter/allByUUID/${schoolUuid}/${page}`
      // )
      .then((response) => {
        const data = response.data
        console.log(data)
        setSchoolName(data.schoolName)
        setTotalCount(data.totalCount)
        setSchoolLetter(data.schoolLetterList)
        setTotalPage(data.totalPage)
        setSchoolId(data.schoolId)
        localStorage.setItem('schoolUuid', schoolUuid)
      })
      .catch((error) => {
        console.error('API 요청 중 오류 발생:', error)
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
        <div className={styleSchool.schoolName}>
          <b>{schoolName}</b>에
          <b>
            <br></br>
            {totalCount}
          </b>
          개의 응원이 왔어요!
        </div>

        <div className={styleSchool.gridContainer}>
          <div
            className={`${styleSchool.arrowIcon} ${
              page === 1 ? styleSchool.disabledArrow : ''
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
                <img src={`${letter.assetImg}`} />
              </div>
            ))}
          </div>
          <div
            className={`${styleSchool.arrowIcon} ${
              page === totalPage ? styleSchool.disabledArrow : ''
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
                X
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
      <div className={styleSchool.btn}>
        <div
          className={styleSchool.mySchoolBtn}
          onClick={() => letterWriteClick(schoolUuid)}
        >
          응원하기
        </div>
        <div className={styleSchool.rowButton}>
          <div className={styleSchool.mySchoolBtnHalfShare} onClick={openModal}>
            공유하기
          </div>

          <div
            className={styleSchool.mySchoolBtnHalf}
            onClick={handleMyDeskClick}
          >
            내 책상 보기
          </div>
        </div>
      </div>
    </div>
  )
}

export default SchoolPage
