import android.content.Context
import android.content.res.AssetManager
import com.example.jettrips.repo.HomePageRepo
import com.example.jettrips.utils.DispatcherProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
class HomePageRepoTest {

    private lateinit var homePageRepo: HomePageRepo
    private lateinit var context: Context
    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var assetManager: AssetManager
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        context = mock(Context::class.java)
        dispatcherProvider = mock(DispatcherProvider::class.java)
        assetManager = mock(AssetManager::class.java)

        `when`(context.assets).thenReturn(assetManager)
        `when`(dispatcherProvider.io).thenReturn(testDispatcher)

        homePageRepo = HomePageRepo(context, dispatcherProvider)

        // Set the main dispatcher to the test dispatcher
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `test getTravelBlogs successfully parses JSON`() = runTest {
        val jsonString =
            """[{"name": "Nomadic Matt", "url": "https://www.nomadicmatt.com", "description": "A popular travel blog."}]"""
        val inputStream = jsonString.byteInputStream()

        `when`(assetManager.open("explore_india.json")).thenReturn(inputStream)

        val result = homePageRepo.getTravelBlogs()

        assertEquals(1, result.size)
        assertEquals("Nomadic Matt", result[0].name)
    }

    @Test
    fun `test getMostVisitedTouristDestination successfully parses JSON`() = runTest {
        val jsonString =
            """[{"name": "Taj Mahal", "location": "Agra", "description": "A historical monument"}]"""
        val inputStream = jsonString.byteInputStream()

        `when`(assetManager.open("travel_blogs.json")).thenReturn(inputStream)

        val result = homePageRepo.getMostVisitedTouristDestination()

        assertEquals(1, result.size)
        assertEquals("Taj Mahal", result[0].name)
    }
}
