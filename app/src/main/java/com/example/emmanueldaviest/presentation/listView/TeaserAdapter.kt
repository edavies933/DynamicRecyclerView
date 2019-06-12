package com.example.emmanueldaviest.presentation.listView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.emmanueldaviest.databinding.SubscribeNowBinding
import com.example.emmanueldaviest.databinding.TeaserItemViewBinding
import com.example.emmanueldaviest.databinding.TeaserItemViewClickedBinding
import com.example.emmanueldaviest.domain.model.Teaser
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit


class TeaserAdapter(var listOfTeasers: MutableList<Teaser>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var disposables: CompositeDisposable = CompositeDisposable()

    @Throws(RuntimeException::class)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when (viewType) {
            TEASER_VIEW -> {

                val binding: TeaserItemViewBinding = TeaserItemViewBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )

                return TeaserViewHolder(binding)

            }
            TEASER_VIEW_CLICKED -> {

                val binding: TeaserItemViewClickedBinding = TeaserItemViewClickedBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )

                return TeaserViewClickedHolder(binding)

            }
            SUBSCRIBE_VIEW -> {

                val binding: SubscribeNowBinding = SubscribeNowBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )
                return TeaserViewClickedHolder(binding)
            }
            else -> throw RuntimeException("view type unknown")
        }

    }

    override fun getItemCount(): Int =
        listOfTeasers.count()


    override fun getItemViewType(position: Int): Int {

        var item = listOfTeasers.get(position)

        return when {
            item.isSubscribeView -> SUBSCRIBE_VIEW
            item.isTabbed -> TEASER_VIEW_CLICKED
            else -> TEASER_VIEW
        }
    }


    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        if (viewHolder.itemViewType == TEASER_VIEW) {
            if (viewHolder is TeaserViewHolder) {
                viewHolder.bind(listOfTeasers[position])
            }

        } else if (viewHolder.itemViewType == TEASER_VIEW_CLICKED) {
            if (viewHolder is TeaserViewClickedHolder) {
                viewHolder.bind(listOfTeasers[position])

            }
        }

    }

    inner class TeaserViewHolder(private val binding: TeaserItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Teaser) {
            binding.teaserViewModel = item


            val teaserView: View by lazy { binding.root }
            addSub(

                teaserView.clicks()
                    .observeOn(Schedulers.io())
                    .	throttleFirst(THRESHOLD_MILLIS,TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        item.isTabbed = true
                        if (item.isPaid!!) {
                            listOfTeasers.add(adapterPosition + 1, Teaser(true))

                            notifyItemInserted(adapterPosition+1)
                        } else
                        notifyDataSetChanged()
                    }
            )
            binding.executePendingBindings()
        }

    }



    inner class TeaserViewClickedHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Teaser) {

            if (binding is TeaserItemViewClickedBinding) {
                binding.teaserViewModel = item
                val clickedTeaserView: View by lazy { binding.root }

                addSub(

                clickedTeaserView.clicks()
                    .observeOn(Schedulers.io())
                    .	throttleFirst(THRESHOLD_MILLIS,TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe{

                        item.isTabbed = !item.isTabbed

                        if (item.isPaid!! && listOfTeasers.count() >= adapterPosition + 1 && listOfTeasers[adapterPosition + 1].isSubscribeView) {

                            listOfTeasers.removeAt(adapterPosition + 1)

                            notifyItemRemoved(adapterPosition + 1)
                        }
                        notifyItemChanged(adapterPosition)
                    }
                )
                binding.executePendingBindings()
            }
        }
    }

    @Synchronized
    private fun addSub(disposable: Disposable?) {
        if (disposable == null) return
        disposables.add(disposable)
    }

    companion object {
        private const val TEASER_VIEW = 1
        private const val TEASER_VIEW_CLICKED = 2
        private const val SUBSCRIBE_VIEW = 3
        private const val THRESHOLD_MILLIS  = 600L
    }

    fun disposeObservables(){
        if (!disposables.isDisposed) disposables.dispose()
    }

}