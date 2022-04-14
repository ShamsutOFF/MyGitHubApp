package ru.gb.android.myapplication.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import ru.gb.android.myapplication.domain.repos.github.GithubUserRepository
import ru.gb.android.myapplication.MyApplication
import ru.gb.android.myapplication.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var githubUserRepository: GithubUserRepository

    private val app by lazy { application as MyApplication }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setScreenState(ScreenState.OK)

        githubUserRepository = app.githubUserRepo

        binding.goBtn.setOnClickListener {
            setScreenState(ScreenState.LOADING)
            binding.resultTv.text = null
            val userName = binding.nameEt.text.toString()
            githubUserRepository.getGithubRepos(userName, { repos ->
                setScreenState(ScreenState.OK)
                binding.resultTv.text = ""
                repos.sortedBy { it.name }.forEach { repo ->
                    binding.resultTv.text = "${binding.resultTv.text}\n${repo.name}"
                }
            }, { thr ->
                setScreenState(ScreenState.ERROR)
                Toast.makeText(this, "Error! ${thr.message}", Toast.LENGTH_SHORT).show()
            })

            githubUserRepository.getAvatar(userName, { avatarUrl ->
                Glide.with(this).load(avatarUrl).into(binding.avatarIv)
            }, {
                Toast.makeText(this, "Error! ${it.message}", Toast.LENGTH_SHORT).show()
            })
        }
    }

    private fun setScreenState(state: ScreenState) {
        when (state) {
            ScreenState.LOADING -> {
                binding.goBtn.isEnabled = false
                binding.goBtn.text = "Please, wait..."
            }
            ScreenState.ERROR -> {
                binding.goBtn.isEnabled = true
                binding.goBtn.text = "Try again"
            }
            ScreenState.OK -> {
                binding.goBtn.isEnabled = true
                binding.goBtn.text = "Go!"
            }
        }
    }

    enum class ScreenState {
        LOADING, ERROR, OK
    }
}