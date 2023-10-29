import { useParams } from 'react-router-dom'
import { IoIosArrowBack } from 'react-icons/io'

const SchoolLetterDetail = () => {
  const { letterId } = useParams()

  return (
    <div>
      <div>
        <IoIosArrowBack />
        이전으로
      </div>
      <div>{letterId}</div>
    </div>
  )
}

export default SchoolLetterDetail
