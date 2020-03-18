import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import olyarisu.github.com.myapplication.data.datasource.DefaultLocalRedditDatasource
import olyarisu.github.com.myapplication.data.datasource.DefaultRemoteRedditDataSource
import olyarisu.github.com.myapplication.data.repository.DefaultSubredditRepository
import olyarisu.github.com.myapplication.domain.entity.Post
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class DefaultSubredditRepositorySpek : Spek({

    val localRedditDatasourceMock = mock<DefaultLocalRedditDatasource>()
    val remoteRedditDataSourceMock = mock<DefaultRemoteRedditDataSource>()
    val coroutineScope = mock<CoroutineScope>()
    val subredditName = "gaming"

    val repository =
        DefaultSubredditRepository(
            localRedditDatasourceMock,
            remoteRedditDataSourceMock,
            coroutineScope,
            subredditName
        )

    // @Rule val rule = InstantTaskExecutorRule()
    beforeEachTest {
        ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
            override fun executeOnDiskIO(runnable: Runnable) {
                runnable.run()
            }

            override fun isMainThread(): Boolean {
                return true
            }

            override fun postToMainThread(runnable: Runnable) {
                runnable.run()
            }
        })

        reset(localRedditDatasourceMock)
    }

    afterEachTest { ArchTaskExecutor.getInstance().setDelegate(null) }

    describe("repository refresh") {
        val error = Throwable()
        val posts = emptyList<Post>()

        it("should load posts from remoteRedditDataSource") {
            runBlockingTest {
                repository.refresh()

                verify(remoteRedditDataSourceMock).loadPosts(eq(subredditName))
            }
        }

        context("when remoteDataSource successfully load posts") {

            it("should update localRedditDatasource with loaded posts") {
                runBlockingTest {
                    whenever(remoteRedditDataSourceMock.loadPosts(subredditName)).thenReturn(posts)
                    repository.refresh()

                    verify(localRedditDatasourceMock).updateBySubreddit(eq(subredditName), eq(posts))
                }
            }
        }

        context("when remoteDataSource load posts with error") {

            it("should not update localRedditDatasource") {
                runBlockingTest {
                    doAnswer { error }
                        .whenever(remoteRedditDataSourceMock)
                        .loadPosts(subredditName)
                    repository.refresh()

                    verify(localRedditDatasourceMock, never()).updateBySubreddit(
                        eq(subredditName),
                        eq(posts)
                    )
                }
            }
        }
    }
})
